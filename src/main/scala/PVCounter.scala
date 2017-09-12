/**
  * Created by yxy on 9/11/17.
  */
import org.apache.spark.sql.SparkSession
import util.UrlUtil

object PVCounter {
  def main(args:Array[String]):Unit={
    val spark= SparkSession.builder().appName("MostUrl").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext

    val fields=List("Time_stamp","Phone","Cityid","IP",
      "Device","Devmac","Apmac","Acmac","Url",
      "Response","Uplink_packets","Uplink_flow","Downlink_packets","Downlink_flow")
    val bcfields=sc.broadcast(fields)

    val data=sc.textFile("hdfs://yxy:9000/user/root/input/data").map(_.split("\t"))
    val urlcounts=data.map{m=>
      val url=m(bcfields.value.indexOf("Url"))
      UrlUtil.getHostName(url)
    }.map(m=>(m,1)).reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false)//.foreach(m=>println(m._1+" url num:"+m._2))
    urlcounts.take(20).foreach(m=>println(m._1+"\t"+m._2))
    //urlcounts.saveAsTextFile("hdfs://yxy:9000/user/output")
  }
}
