package com.demo.spark.utils

import java.util.{Date, Locale}

import org.apache.commons.lang3.time.FastDateFormat

/**
  * 时间解析器
  * 注意SimpleDateFormat是线程不安全的
  */
object DateUtil {

  // 之前的格式: [10/Nov/2016:00:01:02 +0800]
  val perFormat: FastDateFormat = FastDateFormat.getInstance("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH)

  // 需要转成的格式: yyyy-MM-dd HH:mm:ss
  val endFormat: FastDateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss")

  def parse(time: String): String = {
    endFormat.format(new Date(getTime(time)))
  }

  def getTime(time: String): Long = {
    try {
      perFormat.parse(time.substring(time.indexOf("[") + 1, time.lastIndexOf("]"))).getTime
    } catch {
      case e: Exception =>
        e.printStackTrace()
        0l
    }
  }

  def main(args: Array[String]): Unit = {
    val str = parse("[10/Nov/2016:00:01:02 +0800]")
    println(str)
  }

}
