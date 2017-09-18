package main.scala

import com.huaban.analysis.jieba.JiebaSegmenter
import main.scala.util.{FileUtil, StringUtil, UrlUtil}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.ml.feature.{HashingTF, IDF, Normalizer, Tokenizer}
import org.apache.spark.ml.linalg.{Vector => mlV}
import org.apache.spark.mllib.clustering.{GaussianMixture, KMeans, KMeansModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.Row
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by root on 9/13/17.
  */
object UrlClustering {
  val conf = new SparkConf().setAppName("UrlClustering").setMaster("local").set("spark.executor.memory", "1g")
  val sc = new SparkContext(conf)
  val modelPath=Global.outputRoot+"/model"
  def PrepareUrlData(inputPath: String): RDD[(String,String)]= {
    val bcfields = sc.broadcast(List("title","url"))
    val data = sc.textFile(inputPath).map { m =>
      val splits=m.split("\t")
      val title=splits(bcfields.value.indexOf("title"))
      val url=splits(bcfields.value.indexOf("url"))
      (title,url)
    }
    //read in url and their title
    val srcRDD = data
      .map { m =>
        (m._2,StringUtil.ListToString((new JiebaSegmenter).sentenceProcess(m._1)))
      }
    srcRDD
  }
  def run(): Unit ={
    FileUtil.deletehdfsFile(modelPath)
    val resultRDD=clustering(Global.rawUrlPath, Global.labelNum,10,1)
    val fs: FileSystem = FileSystem.get(new java.net.URI(Global.hdfsUrl),new Configuration())
    FileUtil.deletehdfsFile(Global.outputRoot+"/clustering/resultmap")
    resultRDD.map(m=> m._1 + "\t" + m._2)
        .saveAsTextFile(Global.outputRoot+"/clustering/resultmap")
//
//    val urltitlepair=PrepareUrlData(Global.rawUrlPath)
//    val resultRdd=sc.textFile(Global.outputRoot+"/clustering/resultmap").map{m=>
//      val splits=m.split('\t')
//      (splits(1),splits(0).toInt)
//    }.join(urltitlepair).map(m=>(m._2._1,m._1,m._2._2))
//    resultRDD.take(20).foreach(m=>println(m._1+"\t"+m._2+"\t"+m._3))
    val labelRdd=labeling(resultRDD.map(m=>(m._1,m._3)),Global.labelNum)
    FileUtil.deletehdfsFile(Global.outputRoot+"/clustering/labelmap")
    labelRdd.map(m=>(m._1+"\t"+m._2+"\t"+m._3)).saveAsTextFile(Global.outputRoot+"/clustering/labelmap")
  }
  def main(args: Array[String]): Unit = {
    run()
  }
  def labeling(src:RDD[(Int,String)],labelNum:Int): RDD[(Int,String,Int)]={
    var result:Vector[(Int,String,Int)]=Vector()
    for(i <- 0 to labelNum-1){
      val devide=src.filter(_._1==i).map(m=>m._2).flatMap(_.split('\t'))
        .map(m=>(m,1)).reduceByKey(_+_).sortBy(_._2,ascending = false).map(m=>(i,m._1,m._2)).take(10)
        devide.foreach(m=>println(m._1+"\t"+m._2+"\t"+m._3))
        for(record <- devide){
            result=result++Vector(record)
        }
    }
    sc.makeRDD(result)
  }
  def countWords(srcRdd:RDD[String]):Int={
    val counts=srcRdd.flatMap(m=>m.split('\t')).distinct().count()
    var result=counts.toInt
    if(result<=0) result=500000
    result
  }
//  def clustering(srcRDD:RDD[String]):RDD[(Int,String)]={
//    val model=KMeansModel.load(sc,modelPath)
//    val resultRDD=transform(srcRDD).map(m=>(model.predict(m._2),m._1))
//    resultRDD
//  }
  def transform(srcRDD:RDD[(String,String)]):RDD[(String,String,org.apache.spark.mllib.linalg.Vector)]={
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
    val trainingDF = srcRDD.distinct().map{m=>
      RawDataRecord(m._1,m._2)
    }.toDF()
    val tokenizer = new Tokenizer().setInputCol("title").setOutputCol("words")
    val wordsData = tokenizer.transform(trainingDF)
    println("output1：")
    wordsData.select($"title", $"words").show(2)
    //计算每个词在文档中的词频
    val hashingTF = new HashingTF().setNumFeatures(100000).setInputCol("words").setOutputCol("rawFeatures")
    val featurizedData = hashingTF.transform(wordsData)
    println("output2：")
    featurizedData.select($"words", $"rawFeatures").show(2)

    //计算每个词的TF-IDF
    val idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    val idfModel = idf.fit(featurizedData)
    val rescaledData = idfModel.transform(featurizedData)
    println("output3：")
    rescaledData.select($"features").show(2)
    //normalize
    val normalizer = new Normalizer().setInputCol("features").setOutputCol("normFeatures").setP(1.0)
    val normData = normalizer.transform(rescaledData)
    val normFeatures = normData.select($"url",$"title",$"normFeatures").rdd
    val normDataRdd = normFeatures.map {
      case Row(url:String,title:String,normFeatures: mlV) =>
        (url,title,Vectors.dense(normFeatures.toArray))
    }
    normDataRdd
  }

  /**
    * return RDD[(custeringlabel,url,title)]
    * @param inputPath
    * @param k
    * @param ite
    * @param run
    * @return
    */
  def clustering(inputPath: String,k:Int,ite:Int,run:Int): RDD[(Int,String,String)] = {
    val srcRDD = PrepareUrlData(inputPath)
    val normDataRdd=transform(srcRDD)
    val gmm = KMeans.train(normDataRdd.map(m=>m._3),k,ite,run)
    val resultRdd = normDataRdd.map(m => (gmm.predict(m._3),m._1,m._2))
    resultRdd.take(200).foreach(m => println(m._1+"\t"+m._2+"\t"+m._3))
    //resultRdd.map(m=>(m._1+"\t"+m._2)).saveAsTextFile(outputPath+"/resultMap")
    gmm.save(sc,modelPath)
    resultRdd
  }

  case class RawDataRecord(url:String,title: String)

}
