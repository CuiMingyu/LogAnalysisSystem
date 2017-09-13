package main.scala.util

/**
  * Created by root on 9/12/17.
  */
object UrlUtil {
  /**
    * take out the hostname of the url
    * @param url
    * @return
    */
  def getHostName(url:String): String ={
    val splits=url.split('/')
    val weburl=splits(2)
    return weburl
  }
}
