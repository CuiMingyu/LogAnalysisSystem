package main.scala.util

/**
  * Created by root on 9/12/17.
  */
object UrlUtil {
  def getHostName(url:String): String ={
    val splits=url.split('/')
    val weburl=splits(2)
    return weburl
  }
}
