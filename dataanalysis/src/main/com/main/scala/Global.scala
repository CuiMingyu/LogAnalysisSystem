package main.scala

/**
  * Created by root on 9/13/17.
  */
object Global {
  val fields = List("Time_stamp", "Phone", "Cityid", "IP",
    "Device", "Devmac", "Apmac", "Acmac", "Url",
    "Response", "Uplink_packets", "Uplink_flow", "Downlink_packets", "Downlink_flow")
  val hdfsUrl="hdfs://localhost:9000"
  val dbPath=hdfsUrl+"/user/hive/warehouse/loganalysis.db"
  val rawDataPath=dbPath+"/log"
  val rawUrlPath=dbPath+"/url"
  val outputRoot=Global.hdfsUrl+"/LogAnalysisSystem"
  val labelNum=20
  val localPath: String = "tmp/LogAnalysisSystem"
  val activityStatisticDir: String = "/ASOutput"
  val DFPDStatisticDir: String = "/DFPDOutput"
  val NDDStatisticDir: String = "/NDDOutput"
  val TIStatisticDir: String = "/TimeIntervalOutput"
  val PVStatisticDir: String = "/PVOutput"
  val UVStatisticDir: String = "/UVOutput"
  val IPStatisticDir: String = "/IPOutput"
  val UserAnalysisDir: String = "/useranalysis/userpreference"
  val UserDevAnalysisDir: String = "/useranalysis/devMap"
  val clusteringDir:String="/clustering/resultmap"
  val labelingDir:String="/clustering/labelmap"
  val modelDir="/model"
}
