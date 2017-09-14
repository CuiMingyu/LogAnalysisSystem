package main.scala

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.mllib.classification.NaiveBayes
//import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.ml.linalg.{Vector => mlV}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.Row
import com.huaban.analysis.jieba.JiebaSegmenter

/**
  * Created by yxy on 9/12/17.
  */
object TestUrl {

  case class RawDataRecord(category: String, text: String)

  //将java.util.List转化成String格式
  def ListToString(list: java.util.List[String]):String={
    var ans=new String()
    for (elem <- 0 to list.size()-1) {
      ans=ans+'\t'+list.get(elem)
    }
    return ans
  }
  def main(args : Array[String]):Unit= {

    val conf = new SparkConf().setAppName("TestUrl").setMaster("local")
    val sc = new SparkContext(conf)

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._

    //从hdfs上获取分析数据
    var srcRDD = sc.textFile("hdfs://scm001:9000/input/url_trainning_date.txt").map {
      x =>
        var data = x.split("\t")
        var worddata=ListToString((new JiebaSegmenter).sentenceProcess(data(3)))//分词
        RawDataRecord(data(2),worddata)
    }

    //从hdfs上获取测试数据
    var testRDD=sc.textFile("hdfs://scm001:9000/input/url_test_data.txt").map{
      x=>
        var data = x.split("\t")
        var worddata=ListToString((new JiebaSegmenter).sentenceProcess(data(0)))
        RawDataRecord("0",worddata)
    }

    //分析数据和测试数据
    var trainingDF=srcRDD.toDF()
    var testDF=testRDD.toDF()

    //将词语转换成数组
    var tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    var wordsData = tokenizer.transform(trainingDF)
    println("output1：")
    wordsData.select($"category",$"text",$"words").show(2)

    //计算每个词在文档中的词频
    var hashingTF = new HashingTF().setNumFeatures(500000).setInputCol("words").setOutputCol("rawFeatures")
    var featurizedData = hashingTF.transform(wordsData)
    println("output2：")
    featurizedData.select($"category", $"words", $"rawFeatures").show(2)

    //计算每个词的TF-IDF
    var idf = new IDF().setInputCol("rawFeatures").setOutputCol("features")
    var idfModel = idf.fit(featurizedData)
    var rescaledData = idfModel.transform(featurizedData)
    println("output3：")
    rescaledData.select($"category", $"features").show(2)

    //转换成Bayes的输入格式
    var trainDataRdd = rescaledData.select($"category",$"features").map {
      case Row(label: String, features: mlV) =>
        LabeledPoint(label.toDouble, (Vectors.dense(features.toArray)))
    }.rdd
    println("output4：")
    trainDataRdd.take(1).foreach(println(_))

    //训练模型
    val model = NaiveBayes.train(trainDataRdd, lambda = 1.0, modelType = "multinomial")

    //测试数据集，做同样的特征表示及格式转换
    var testwordsData = tokenizer.transform(testDF)
    var testfeaturizedData = hashingTF.transform(testwordsData)
    var testrescaledData = idfModel.transform(testfeaturizedData)
    var testDataRdd = testrescaledData.select($"category",$"features").map {
      case Row(label: String, features: mlV) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    //对测试数据集使用训练模型进行分类预测
    val testpredictionAndLabel = testDataRdd.map(p => (model.predict(p.features), p.label))//注意模型预测时的数据输入合适

    //输出计算结果
    testpredictionAndLabel.rdd.foreach(x=>println(x._1.toInt))

  }
}
