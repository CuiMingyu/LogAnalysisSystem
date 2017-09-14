package main.scala

import com.huaban.analysis.jieba.JiebaSegmenter
import main.scala.util.{StringUtil, UrlUtil}
import org.apache.spark.ml.feature.{HashingTF, IDF, Normalizer, Tokenizer}
import org.apache.spark.ml.linalg.{Vector => mlV}
import org.apache.spark.mllib.clustering.GaussianMixture
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.{SparkConf, SparkContext}
/**
  * Created by root on 9/13/17.
  */
object UrlClustering {

  case class RawDataRecord(text: String)
  def PrepareUrlData(inputPath:String,outputPath:String): Unit ={
    val conf = new SparkConf().setAppName("PrepareUrlData").setMaster("local").set("spark.executor.memory","1g")
    val sc = new SparkContext(conf)
    val bcfields = sc.broadcast(Global.fields)
    val data = sc.textFile(inputPath).map(_.split("\t"))
    //read in url and get their title
    val titleUrlPair = data.map { m =>
      m(bcfields.value.indexOf("Url"))
    }.distinct().map(m => UrlUtil.getTitle(m)).filter(_ !=null) //delete elements whose title is null
    titleUrlPair.repartition(1).saveAsTextFile(outputPath)
  }
  def clustering(inputPath: String, outputPath: String): Unit = {
    val conf = new SparkConf().setAppName("UrlClustering").setMaster("local").set("spark.executor.memory","1g")
    val sc = new SparkContext(conf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    val bcfields = sc.broadcast(Global.fields)
    val data = sc.textFile(inputPath).map(_.split("\t"))
    //read in url and get their title
    val titleUrlPair = data.map { m =>
      m(3)
    }//.distinct().map(m => UrlUtil.getTitle(m)).filter(_ !=null) //delete elements whose title is null
    val srcRDD = titleUrlPair
      .map { m =>
        val str = StringUtil.ListToString((new JiebaSegmenter).sentenceProcess(m))
        RawDataRecord(str)
      }
    val trainingDF = srcRDD.toDF()

    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val wordsData = tokenizer.transform(trainingDF)
    println("output1：")
    wordsData.select($"text", $"words").show(2)
    //计算每个词在文档中的词频
    val hashingTF = new HashingTF().setNumFeatures(1000).setInputCol("words").setOutputCol("rawFeatures")
    val featurizedData = hashingTF.transform(wordsData)
    println("output2：")
    featurizedData.select( $"words", $"rawFeatures").show(2)

    //计算每个词的TF-IDF
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)
    println("output3：")
    rescaledData.select($"features").show(2)
    //normalize
    val normalizer = new Normalizer().setInputCol("features").setOutputCol("normFeatures").setP(1.0)
    val normData = normalizer.transform(rescaledData)
    val normFeatures = normData.select($"normFeatures").rdd
    val normDataRdd=normFeatures.map {
      case Row(normFeatures: mlV) =>
        Vectors.dense(normFeatures.toArray)
    }
    val gmm = new GaussianMixture().setK(10).run(normDataRdd)
    val resultRdd=normDataRdd.map(m => gmm.predict(m))
    resultRdd.foreach(m=>println(m))
  }
  def main(args:Array[String]): Unit ={
    //PrepareUrlData("hdfs://scm001:9000/user/hive/warehouse/loganalysis.db/log/log.txt",
    //"hdfs://scm001:9000/LogAnalysisSystem/TitleList/part1")
    clustering("hdfs://scm001:9000/input/url_trainning_date.txt",null)
  }
}
