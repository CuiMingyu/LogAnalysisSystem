package main.scala

import org.apache.spark.sql.SparkSession

import scala.collection.mutable

/**
  * Created by yxy on 9/12/17.
  */
object APPCount {
  def main(args:Array[String]):Unit={
    val spark= SparkSession.builder().appName("APPCount").master("local").enableHiveSupport().getOrCreate()
    val sc=spark.sparkContext
    val data=sc.textFile("hdfs://yxy:9000/user/root/input/day_03_data").map(_.split(","))
    val appdata=data.map{m=>
      val appname=m(3)
      val byte=m(5).toInt+m(6).toInt
      (appname,byte)
    }.reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false)
    appdata.foreach(m=>println(m._2+" user num:"+m._1))
    val test:Map[String,String]=Map("changba"->"K歌","12306"->"出行","suanya"->"出行",
    "ctrip"->"旅游","qunar"->"旅游","jd"->"购物","tmall"->"购物","meiyan"->"拍照",
    "poco"->"拍照","qq"->"社交","wechat"->"社交","inke"->"直播","yy"->"直播","douyu"->"直播")
    val labeldata=data.map{m=>
      val labelname=test.apply(m(3))
      val byte=m(5).toInt+m(6).toInt
      (labelname,byte)
    }.reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false)
    labeldata.foreach(m=>println(m._2+" user num:"+m._1))
  }
}
