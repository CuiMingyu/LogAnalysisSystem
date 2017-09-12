package main.scala
import org.apache.spark.sql.SparkSession
import main.scala.util.UrlUtil

/**
  * Created by root on 9/12/17.
  */
object UVCounter {
  def run(inputPath:String,outputPath:String,num: Int): Unit ={
    val spark= SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext

    val fields=List("Time_stamp","Phone","Cityid","IP",
      "Device","Devmac","Apmac","Acmac","Url",
      "Response","Uplink_packets","Uplink_flow","Downlink_packets","Downlink_flow")
    val bcfields=sc.broadcast(fields)
    val data=sc.textFile(inputPath).map(_.split("\t"))
    val uvcounts=data.map{m=>
      val url=m(bcfields.value.indexOf("Url"))
      val mac=m(bcfields.value.indexOf("Devmac"))
      (UrlUtil.getHostName(url),mac)
    }.distinct().map(m=> (m._1,1)).reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false).take(num)
    sc.makeRDD(uvcounts).map(m=> m._2+"\t"+m._1).repartition(1).saveAsTextFile(outputPath)
  }
  def main(args:Array[String]): Unit ={
    run("hdfs://scm001:9000/user/hive/warehouse/loganalysis.db/log",
      "hdfs://scm001:9000/LogAnalysisSystem/UVOutput",20)
  }
}
