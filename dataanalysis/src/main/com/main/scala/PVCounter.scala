/**
  * Created by yxy on 9/11/17.
  */
package main.scala

import main.scala.util.UrlUtil
import org.apache.spark.sql.SparkSession

object PVCounter {
  def main(args: Array[String]): Unit = {
    run()
  }
  def run(): Unit ={
    run(Global.rawDataPath,
      Global.outputRoot+Global.PVStatisticDir, 20)
  }
  def run(inputPath: String, outputPath: String, num: Int) {
    val spark = SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc = spark.sparkContext
    val bcfields = sc.broadcast(Global.fields)

    val data = sc.textFile(inputPath).map(_.split("\t"))
    //counts the PV of each hosts and sorts by it, then take the top [num] (host,PV) pairs
    val pvcounts = data.map { m =>
      val url = m(bcfields.value.indexOf("Url"))
      UrlUtil.getHostName(url)
    }.map(m => (m, 1)).reduceByKey(_ + _).map(m => (m._2, m._1)).sortByKey(ascending = false).take(num)
    //save on hdfs
    sc.makeRDD(pvcounts).map(m => m._2 + "\t" + m._1).repartition(1).saveAsTextFile(outputPath)
  }
}
