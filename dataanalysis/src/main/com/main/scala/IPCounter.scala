package main.scala

import main.scala.util.UrlUtil
import org.apache.spark.sql.SparkSession

/**
  * Created by root on 9/12/17.
  */
object IPCounter {
  def main(args: Array[String]): Unit = {
    run()
  }
  def run(): Unit ={
    run(Global.rawDataPath,
      Global.outputRoot+"/IPOutput", 20)
  }
  def run(inputPath: String, outputPath: String, num: Int) {
    val spark = SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc = spark.sparkContext
    val bcfields = sc.broadcast(Global.fields)
    val data = sc.textFile(inputPath).map(_.split("\t"))
    //counts the IP of each hosts and sorts by it, then take the top [num] (host,IP) pairs
    val ipcounts = data.map { m =>
      val url = m(bcfields.value.indexOf("Url"))
      val ip = m(bcfields.value.indexOf("IP"))
      (UrlUtil.getHostName(url), ip)
    }.distinct().map(m => (m._1, 1)).reduceByKey(_ + _).map(m => (m._2, m._1)).sortByKey(ascending = false).take(num)
    //save on hdfs
    sc.makeRDD(ipcounts).map(m => m._2 + "\t" + m._1).repartition(1).saveAsTextFile(outputPath)
  }
}
