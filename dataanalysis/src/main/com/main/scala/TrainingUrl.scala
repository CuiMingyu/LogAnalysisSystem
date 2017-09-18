package main.scala

/**
  * Created by yxy on 9/12/17.
  */

import org.apache.spark.ml.feature.{HashingTF, IDF, Tokenizer}
import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.{SparkConf, SparkContext}

//import org.apache.spark.mllib.linalg.Vector
import com.huaban.analysis.jieba.JiebaSegmenter
import org.apache.spark.ml.linalg.{Vector => mlV}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.sql.Row


object TrainingUrl {

  def main(args: Array[String]) {

    val conf = new SparkConf().setAppName("TrainingUrl").setMaster("local")
    val sc = new SparkContext(conf)

    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._

    var srcRDD = sc.textFile("hdfs://scm001:9000/input/url_trainning_date.txt").map {
      x =>
        var data = x.split("\t")
        var worddata = ListToString((new JiebaSegmenter).sentenceProcess(data(3)))
        RawDataRecord(data(2), worddata)
    }


    //70%作为训练数据，30%作为测试数据
    val splits = srcRDD.randomSplit(Array(0.7, 0.3))
    var trainingDF = splits(0).toDF()
    var testDF = splits(1).toDF()

    //将词语转换成数组
    var tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    var wordsData = tokenizer.transform(trainingDF)
    println("output1：")
    wordsData.select($"category", $"text", $"words").show(2)

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
    var trainDataRdd = rescaledData.select($"category", $"features").map {
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
    var testDataRdd = testrescaledData.select($"category", $"features").map {
      case Row(label: String, features: mlV) =>
        LabeledPoint(label.toDouble, Vectors.dense(features.toArray))
    }

    //对测试数据集使用训练模型进行分类预测
    val testpredictionAndLabel = testDataRdd.map(p => (model.predict(p.features), p.label)) //注意模型预测时的数据输入合适
    //统计分类准确率
    //println( testDataRdd.rdd.count())
    //println(testpredictionAndLabel.rdd.filter(x => x._1 == x._2).count())
    var testaccuracy = 1.0 * testpredictionAndLabel.rdd.filter(x => x._1 == x._2).count() / testDataRdd.rdd.count()
    println("准确率为 ：" + testaccuracy)


  }

  def ListToString(list: java.util.List[String]): String = {
    var ans = new String()
    for (elem <- 0 to list.size() - 1) {
      ans = ans + '\t' + list.get(elem)
    }
    return ans
  }

  case class RawDataRecord(category: String, text: String)

}

