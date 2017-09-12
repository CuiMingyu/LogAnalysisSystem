/**
  * Created by yxy on 9/11/17.
  */
import org.apache.spark.sql.SparkSession
import org.apache.hadoop.fs._

object MostUrl{
  def main(args:Array[String]):Unit={
    val spark= SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext

    val fields=List("Time_stamp","Phone","Cityid","IP",
      "Device","Devmac","Apmac","Acmac","Url",
      "Response","Uplink_packets","Uplink_flow","Downlink_packets","Downlink_flow")
    val bcfields=sc.broadcast(fields)
    val inputPath="hdfs://scm001:9000/user/hive/warehouse/loganalysis.db/log"
    //val hdfs=FileSystem.get(new java.net.URI(inputPath),new Configuration())
    val data=sc.textFile(inputPath).map(_.split("\t"))
    data.map{m=>
      val url=m(bcfields.value.indexOf("Url"))
      val weburl=url.split('/')(2)
      weburl
    }.map(m=>(m,1)).reduceByKey(_+_).foreach(m=>println(m._1+" url num:"+m._2))
  }
}
