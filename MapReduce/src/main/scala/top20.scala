package main.scala.spark_ml_0

import org.apache.spark.sql.SparkSession

object top20 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("top20").master("local").enableHiveSupport().getOrCreate()
    val sc = spark.sparkContext

    //生成广播变量
    val fields = List("time_stamp", "phone", "city_id", "IP", "device", "devmac", "apmac",
      "acmac", "url", "response", "uplink_packets", "uplink_flow", "downlink_packets", "downlink_flow")
    val bcfields = sc.broadcast(fields)

    //对APP字段访问次数进行统计，并进行排序
    val inputPath = "hdfs://scm001:9000/user/hive/warehouse/loganalysis/log/"
    val mobile = sc.textFile(inputPath).map(_.split("\t"))
    mobile.map(m => (m(bcfields.value.indexOf("devmac")), 1))
      .reduceByKey(_ + _).map(m => (m._2, m._1)).sortByKey(false).map(m => (m._2, m._1)).collect()
      .foreach(m => println(m._1 + "的访问次数是：" + m._2))
    /*
    //移动互联网日活跃用户（DAU）的统计
    mobile.map{m =>
      val imei = m(bcfields.value.indexOf("IMEI"))//取出IMEI
    val time = m(bcfields.value.indexOf("Time"))//取出Time
      imei + ":" + time
    }.distinct().map(_.split(":")).map(m => (m(1),1)).reduceByKey(_+_)
      .foreach(m => println(m._1 + "日活动用户量是：" + m._2))

    //移动互联网月活跃用户（MAU）的统计
    mobile.map{m =>
      val imei = m(bcfields.value.indexOf("IMEI"))//取出IMEI
    val time = m(bcfields.value.indexOf("Time")).substring(0,7)//取出Time
      imei + ":" + time
    }.distinct().map(_.split(":")).map(m => (m(1),1)).reduceByKey(_+_)
      .foreach(m => println(m._1 + "月活动用户量是：" + m._2))

    //统计在不同应用中的上行流量
    mobile.map(m => (m(bcfields.value.indexOf("APP")), m(bcfields.value.indexOf("UplinkBytes")).toInt))
      .reduceByKey(_+_).foreach(m => println(m._1 + "应用上行的流量是：" + m._2))

    //统计在不同应用中的下行流量
    mobile.map(m => (m(bcfields.value.indexOf("APP")), m(bcfields.value.indexOf("DownlinkBytes")).toInt))
      .reduceByKey(_+_).foreach(m => println(m._1 + "应用下行的流量是：" + m._2))

    //统计在不同应用中的上下行流量
    mobile.map(m => (m(bcfields.value.indexOf("APP")),
      List(m(bcfields.value.indexOf("UplinkBytes")).toInt, m(bcfields.value.indexOf("DownlinkBytes")).toInt)))
      .reduceByKey((a, b) => List(a(0)+b(0), a(1)+b(1)))
      .foreach(m => println(m._1 + "应用上行的流量是：" + m._2(0) + "\t" +
        "下行的流量是：" + m._2(1)))
  }*/
  }
}
