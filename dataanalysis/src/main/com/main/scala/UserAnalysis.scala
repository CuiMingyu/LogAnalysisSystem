package main.scala

import main.java.dao.LoadDataDAO
import main.java.service.StatisticService
import main.java.util.Sqldb
import main.scala.util.{FileUtil, StringUtil}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.FileSystem
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
  * Created by root on 9/17/17.
  */
object UserAnalysis {
  val conf = new SparkConf().setAppName("UserAnalysis").setMaster("local").set("spark.executor.memory", "1g")
  val sc = new SparkContext(conf)
  var inputPath=Global.rawDataPath
  var clusteringInfoPath=Global.outputRoot+Global.clusteringDir
  var outputPath=Global.outputRoot
  var labelNum=Global.labelNum
  /**
    * calculate the preference level of users
    * @param srcRDD
    * @param cPath
    * @return
    */
  def userPreferenceStatistic(srcRDD:RDD[String],cPath:String,labelNum:Int): RDD[(String,Int,Int)]={
    val bcfields=sc.broadcast(Global.fields)
    val labelMap=sc.textFile(cPath).map{m=>
      val splits=m.split('\t')
      (splits(1),splits(0).toInt)   //(url,label)
    }
    println(labelMap.count() )
    val flowRDD=srcRDD.map{m=>
      val splits=m.split('\t')
      val phone=splits(bcfields.value.indexOf("Phone"))
      val url=splits(bcfields.value.indexOf("Url"))
      val uplink=StringUtil.toInt(splits(bcfields.value.indexOf("Uplink_flow")))
      val downlink=StringUtil.toInt(splits(bcfields.value.indexOf("Downlink_flow")))
      val total=uplink+downlink
      (url,(phone,total))
    }.filter(_._2._2!=0).join(labelMap).map(m=>m._2)
      .map(m=>((m._1._1,m._2),m._1._2.toLong)).reduceByKey(_+_)
      //.map(m=>((m._1._2,m._2),m._1._1)).sortByKey(ascending=false)
      //.saveAsTextFile(outputPath+"/sortedphonelist")
    var result:Vector[(String,Int,Int)]=Vector()
    for(i <- 0 to labelNum-1){
      val part=flowRDD.filter(_._1._2==i).map(m=>(m._1._1,m._2))
        .sortBy(_._2,ascending=false).collect
      val num=part.length
      for(j <- 1 to num){
        val elem=part(j-1)
        if(j<=num*1/3){
          result=result++Vector((elem._1,i,2))
        }
        else if(j<=num*2/3){
          result=result++Vector((elem._1,i,1))
        }
        else result=result++Vector((elem._1,i,0))
      }
    }
    sc.makeRDD(result)
  }
  def run(inputPath:String,outputPath:String,clusteringInfoPath:String,labelNum:Int): Unit ={
    val srcRDD=sc.textFile(inputPath)
    val resultRDD=userPreferenceStatistic(srcRDD,clusteringInfoPath,labelNum)
    FileUtil.deletehdfsFile(outputPath+Global.UserAnalysisDir)
    resultRDD.map(m=>m._1+"\t"+m._2+"\t"+m._3).saveAsTextFile(outputPath+Global.UserAnalysisDir)
  }
  def run(): Unit ={
    run(inputPath,outputPath,clusteringInfoPath,labelNum)
  }
  def main(args:Array[String]): Unit ={
    run()
    val fs: FileSystem = FileSystem.get(new java.net.URI(Global.hdfsUrl),new Configuration())
    StatisticService.HDFSTolocal(fs,outputPath,Global.localPath+Global.UserAnalysisDir)
    var conn = Sqldb.getDefaultConnection
    LoadDataDAO.loadIntoPreferenceTable(conn,Global.localPath+Global.UserAnalysisDir)
    Sqldb.closeConnection(conn)

  }
}
