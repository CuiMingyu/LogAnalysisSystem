package main.scala.util

/**
  * Created by root on 9/13/17.
  */
object StringUtil {
  def ListToString(list: java.util.List[String]):String={
    var ans=new String()
    for (elem <- 0 to list.size()-1) {
      ans=ans+'\t'+list.get(elem)
    }
    return ans
  }
}
