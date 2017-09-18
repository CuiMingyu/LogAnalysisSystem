package main.scala.util

import main.scala.Global
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}

/**
  * Created by root on 9/18/17.
  */
object FileUtil {
  def deletehdfsFile(path:String):Unit={
    val fs: FileSystem = FileSystem.get(new java.net.URI(Global.hdfsUrl),new Configuration())
    fs.delete(new Path(path),true)
    fs.close()
  }
}
