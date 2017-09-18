package main.scala

import main.scala.util.FileUtil
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by root on 9/18/17.
  */
object UserDevAnalysis {
  val conf = new SparkConf().setAppName("UserAnalysis").setMaster("local").set("spark.executor.memory", "1g")
  val sc = new SparkContext(conf)
  val inputPath=Global.rawDataPath
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
    run(inputPath,outputPath)
  }
  def run(inputPath:String,outputPath:String): Unit ={
    val srcRDD=sc.textFile(inputPath)
    val devMap=devAnalysis(srcRDD)
    FileUtil.deletehdfsFile(outputPath+"/devMap")
    devMap.map(m=>m._1+"\t"+m._2).saveAsTextFile(outputPath+"/devMap")
  }
  def main(args:Array[String]): Unit ={
    run()
  }
}
