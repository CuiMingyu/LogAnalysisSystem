package main.scala

import org.apache.spark.sql.SparkSession

/**
  * Created by yxy on 9/12/17.
  */
object APPCount {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("APPCount").master("local").enableHiveSupport().getOrCreate()
    val sc = spark.sparkContext
    val data = sc.textFile("hdfs://scm001:9000/input/day_03_data").map(_.split(","))
    val appdata = data.map { m =>
      val appname = m(3)
      val byte = m(5).toInt + m(6).toInt
      (appname, byte)
    }.reduceByKey(_ + _).map(m => (m._2, m._1)).sortByKey(ascending = false)
    appdata.foreach(m => println(m._2 + " user num:" + m._1))

    val test: Map[String, String] = Map("changba" -> "K歌", "12306" -> "出行", "suanya" -> "出行",
      "ctrip" -> "旅游", "qunar" -> "旅游", "jd" -> "购物", "tmall" -> "购物", "meiyan" -> "拍照",
      "poco" -> "拍照", "qq" -> "社交", "wechat" -> "社交", "inke" -> "直播", "yy" -> "直播", "douyu" -> "直播")
    val labeldata = data.map { m =>
      val labelname = test.apply(m(3))
      val byte = m(5).toInt + m(6).toInt
      (labelname, byte)
    }.reduceByKey(_ + _).map(m => (m._2, m._1)).sortByKey(ascending = false)
    labeldata.foreach(m => println(m._2 + " user num:" + m._1))

    val usernum = data.map { m =>
      val labelname = test.apply(m(3))
      val user = m(2)
      (labelname, user)
    }.distinct().map(m => (m._1, 1)).reduceByKey(_ + _)
    usernum.foreach(m => println(m))

    val userdata = data.map { m =>
      val labelname = test.apply(m(3))
      val user = m(2)
      val byte = m(5).toInt + m(6).toInt
      ((labelname, user), byte)
    }.reduceByKey(_ + _).map(m => (m._2, m._1)).sortByKey(ascending = false)
    userdata.foreach(m => println(m._2 + " user num:" + m._1))

    val list = List("社交", "K歌", "旅游", "直播", "出行", "购物", "拍照")
    for (elem <- list) {
      println(elem + ":")
      val array = usernum.filter(m => m._1 == elem).map(m => m._2).collect
      val low = (array(0).toDouble / 3).toInt
      val high = (array(0).toDouble / 3 * 2).toInt
      val labelcount = userdata.filter(m => m._2._1 == elem).sortByKey(ascending = false)
      for (i <- 0 to labelcount.collect.length - 1) {
        if (i >= high - 1) {
          val printlabel = labelcount.collect.apply(i)
          println("用户" + printlabel._2._2 + "是高频用户")
        }
        else if (i < low - 1) {
          val printlabel = labelcount.collect.apply(i)
          println("用户" + printlabel._2._2 + "是低频用户")
        }
        else {
          val printlabel = labelcount.collect.apply(i)
          println("用户" + printlabel._2._2 + "是中频用户")
        }
      }
    }
  }
}
