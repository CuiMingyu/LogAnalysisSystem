package main.scala

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * Created by root on 9/17/17.
  */
object UserAnalysis {
  val conf = new SparkConf().setAppName("UserAnalysis").setMaster("local").set("spark.executor.memory", "1g")
  val sc = new SparkContext(conf)
  val inputPath=Global.rawDataPath
  val clusteringInfoPath=Global.outputRoot+"clustering/resultmap"
  val outputPath=Global.outputRoot+"/useranalysis"
  def devAnalysis(srcRDD:RDD[String]): RDD[(String,String)] ={
    val bcfields=sc.broadcast(Global.fields)
    val resultRDD=srcRDD.map{m=>
      val splits=m.split('\t')
      val phone =splits(bcfields.value.indexOf("Phone"))
      val dev=splits(bcfields.value.indexOf("Device"))
      (phone,dev)
    }.distinct()
    resultRDD
  }
  def run(): Unit ={
    val srcRDD=sc.textFile(inputPath)
    val devMap=devAnalysis(srcRDD)
    devMap.map(m=>m._1+"\t"+m._2).saveAsTextFile(outputPath+"/devMap")

  }

}
