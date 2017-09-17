package main.scala

/**
  * Created by root on 9/13/17.
  */
object Global {
  val fields = List("Time_stamp", "Phone", "Cityid", "IP",
    "Device", "Devmac", "Apmac", "Acmac", "Url",
    "Response", "Uplink_packets", "Uplink_flow", "Downlink_packets", "Downlink_flow")
  val hdfsUrl="hdfs://scm001:9000"
  val dbPath=hdfsUrl+"/user/hive/warehouse/loganalysis.db"
  val rawDataPath=dbPath+"/log"
  val rawUrlPath=dbPath+"/url"
  val outputRoot=Global.hdfsUrl+"/LogAnalysis"
}
