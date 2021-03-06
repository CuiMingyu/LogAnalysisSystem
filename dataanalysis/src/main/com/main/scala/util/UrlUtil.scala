package main.scala.util

import main.java.util.Spider

/**
  * Created by root on 9/12/17.
  */
object UrlUtil {
  /**
    * take out the hostname of the url
    *
    * @param url
    * @return
    */
  def getHostName(url: String): String = {
    val splits = url.split('/')
    val weburl = splits(2)
    weburl
  }

  def getTitle(url: String): String = {
    Spider.getTitle(url)
  }
}
