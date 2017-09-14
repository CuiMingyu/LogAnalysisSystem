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
    //对所有app进行流量和排名
    val appdata=data.map{m=>
      val appname=m(3)
      val byte=m(5).toInt+m(6).toInt
      (appname,byte)
    }.reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false)
    appdata.foreach(m=>println(m._2+" user num:"+m._1))

    //用Map将app与标签对应，计算并排名所有标签的流量和
    val test:Map[String,String]=Map("changba"->"K歌","12306"->"出行","suanya"->"出行",
    "ctrip"->"旅游","qunar"->"旅游","jd"->"购物","tmall"->"购物","meiyan"->"拍照",
    "poco"->"拍照","qq"->"社交","wechat"->"社交","inke"->"直播","yy"->"直播","douyu"->"直播")
    val labeldata=data.map{m=>
      val labelname=test.apply(m(3))
      val byte=m(5).toInt+m(6).toInt
      (labelname,byte)
    }.reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false)
    labeldata.foreach(m=>println(m._2+" user num:"+m._1))

    //计算每个标签的不同用户数量
    val usernum=data.map{m=>
      val labelname=test.apply(m(3))
      val user=m(2)
      (labelname,user)
    }.distinct().map(m=>(m._1,1)).reduceByKey(_+_)
    usernum.foreach(m=>println(m))

    //将标签和用户作为一个键值对，再与流量和建立一个大键值对进行不同标签不同用户的流量和计算
    val userdata=data.map{m=>
      val labelname=test.apply(m(3))
      val user=m(2)
      val byte=m(5).toInt+m(6).toInt
      ((labelname,user),byte)
    }.reduceByKey(_+_).map(m=>(m._2,m._1)).sortByKey(ascending=false)
    userdata.foreach(m=>println(m._2+" user num:"+m._1))

    //统计每个标签的使用用户的排名情况分为高中低频用户
    val list=List("社交","K歌","旅游","直播","出行","购物","拍照")
    //var plist=new String()
    for(elem <- list){
      //plist=plist+elem.toString()+":\t"
      println(elem+":")
      val array=usernum.filter(m=>m._1==elem).map(m=>m._2).collect
      val low=(array(0).toDouble/3).toInt
      val high=(array(0).toDouble/3*2).toInt
      val labelcount=userdata.filter(m=>m._2._1==elem).sortByKey(ascending = false)
      for(i<-0 to labelcount.collect.length-1){
        if(i>=high) {
          val printlabel=labelcount.collect.apply(i)
          //plist=plist+"用户"+printlabel._2._2+"是高频用户\t"
          println("用户"+printlabel._2._2+"是高频用户")
        }
        else if(i<=low){
          val printlabel=labelcount.collect.apply(i)
          //plist=plist+"用户"+printlabel._2._2+"是低频用户\t"
          println("用户"+printlabel._2._2+"是低频用户")
        }
        else{
          val printlabel=labelcount.collect.apply(i)
          //plist=plist+"用户"+printlabel._2._2+"是中频用户\t"
          println("用户"+printlabel._2._2+"是中频用户")
        }
      }
      //println(plist)
    }
  }
}
