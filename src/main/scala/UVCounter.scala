import org.apache.spark.sql.SparkSession
import util.UrlUtil

/**
  * Created by root on 9/12/17.
  */
object UVCounter {
  def run(): Unit ={
    val spark= SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext

    val fields=List("Time_stamp","Phone","Cityid","IP",
      "Device","Devmac","Apmac","Acmac","Url",
      "Response","Uplink_packets","Uplink_flow","Downlink_packets","Downlink_flow")
    val bcfields=sc.broadcast(fields)
    val inputPath="hdfs://scm001:9000/user/hive/warehouse/loganalysis.db/log"
    val data=sc.textFile(inputPath).map(_.split("\t"))
    val uvcounts=data.map{m=>
      val url=m(bcfields.value.indexOf("Url"))
      val mac=m(bcfields.value.indexOf("Devmac"))
      (UrlUtil.getHostName(url),mac)
    }.distinct().map(m=> (m._1,1)).reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false)
    //uvcounts.foreach(m => println(m._1+" uv: "+m._2))
  }
  def main(args:Array[String]): Unit ={
    run()
  }
}
