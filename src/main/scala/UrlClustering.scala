package main.scala

import com.huaban.analysis.jieba.JiebaSegmenter
import main.scala.util.{StringUtil, UrlUtil}
import org.apache.spark.ml.feature.Tokenizer
import org.apache.spark.ml.linalg.{Vector => mlV}
import org.apache.spark.mllib.clustering.GaussianMixture
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by root on 9/13/17.
  */
object UrlClustering {
  case class RawDataRecord(text: String)

  def prepareData(inputPath:String): RDD[String] ={
    val spark= SparkSession.builder().appName("PrepreUrlData").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext
    val bcfields=sc.broadcast(Global.fields)
    val data=sc.textFile(inputPath).map(_.split("\t"))
    val titleUrlPair=data.map{m=>
      val url=m(bcfields.value.indexOf("Url"))
      UrlUtil.getTitle(url)
    }.distinct()
      .filter(_!=null) //delete elements whose title is null
    titleUrlPair
  }
  def clustering(inputPath:String,outputPath:String): Unit ={
    val conf = new SparkConf().setAppName("UrlCluster").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    //val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    var srcRDD=prepareData(inputPath)
      .map{ m=>
        val str=StringUtil.ListToString((new JiebaSegmenter).sentenceProcess(m))
        RawDataRecord(str)
      }
    var trainingDF=srcRDD.toDF()
    //fen ci
    var tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    var wordsData = tokenizer.transform(trainingDF)
    println("output1：")
    wordsData.select($"text",$"words").show(2)
    val gmm=new GaussianMixture().setK(10)//.run(data)
  }
}
