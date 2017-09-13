/**
  * Created by yxy on 9/11/17.
  */
package main.scala
import org.apache.spark.sql.SparkSession
import main.scala.util.UrlUtil

object PVCounter {
  def run(inputPath:String,outputPath:String,num: Int){
    val spark= SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext
    val fields=List("Time_stamp","Phone","Cityid","IP",
      "Device","Devmac","Apmac","Acmac","Url",
      "Response","Uplink_packets","Uplink_flow","Downlink_packets","Downlink_flow")
    val bcfields=sc.broadcast(fields)

    val data=sc.textFile(inputPath).map(_.split("\t"))
    //counts the PV of each hosts and sorts by it, then take the top [num] (host,PV) pairs
    val pvcounts=data.map{m=>
      val url=m(bcfields.value.indexOf("Url"))
      UrlUtil.getHostName(url)
    }.map(m=>(m,1)).reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false).take(num)
    //save on hdfs
    sc.makeRDD(pvcounts).map(m => m._2+"\t"+m._1).repartition(1).saveAsTextFile(outputPath)
  }
  def main(args:Array[String]):Unit={
    run("hdfs://scm001:9000/user/hive/warehouse/loganalysis.db/log",
      "hdfs://scm001:9000/LogAnalysisSystem/PVOutput",20)
  }
}
