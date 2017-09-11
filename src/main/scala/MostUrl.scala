/**
  * Created by yxy on 9/11/17.
  */
import org.apache.spark.sql.SparkSession

object MostUrl {
  def main(args:Array[String]):Unit={
    val spark= SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext

    val fields=List("Time_stamp","Phone","Cityid","IP",
      "Device","Devmac","Apmac","Acmac","Url",
      "Response","Uplink_packets","Uplink_flow","Downlink_packets","Downlink_flow")
    val bcfields=sc.broadcast(fields)

    val data=sc.textFile("hdfs://yxy:9000/user/root/input/data").map(_.split("\t"))
    data.map{m=>
      val url=m(bcfields.value.indexOf("URL"))
      val weburl=url.substring(0,url.indexOf('/',3))
      weburl
    }.distinct().map(m=>(m(1),1)).reduceByKey(_+_).foreach(m=>println(m._1+"url num:"+m._2))
  }
}
