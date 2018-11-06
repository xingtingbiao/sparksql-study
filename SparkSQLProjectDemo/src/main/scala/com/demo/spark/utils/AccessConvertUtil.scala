package com.demo.spark.utils

import org.apache.spark.sql.Row
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

/**
  * 日志转换的工具类
  */
object AccessConvertUtil {

  val struct = StructType(
    Array(
      StructField("url", StringType),
      StructField("cmsType", StringType),
      StructField("cmsId", LongType),
      StructField("flow", LongType),
      StructField("ip", StringType),
      StructField("city", StringType),
      StructField("time", StringType),
      StructField("day", StringType)
    )
  )

  /**
    * 将输入的每一行日志转换成想要的输出
    * @param line 输入的每一行日志
    */
  def parseLog(line: String) = {
    try {
      val splits = line.split("\t")
      val url = splits(1)
      val domain = "http://www.imooc.com/"
      val cms = url.substring(url.indexOf(domain) + domain.length).split("/")
      var cmsType = ""
      var cmsId = 0l
      if (cms.length > 1) {
        cmsType = cms(0)
        cmsId = cms(1).toLong
      }
      val flow = splits(2).toLong
      val ip = splits(3)
      val city = IpUtils.getCity(ip)
      val time = splits(0)
      val day = splits(0).substring(0, 10).replaceAll("-", "")
      Row(url, cmsType, cmsId, flow, ip, city, time, day)
    } catch {
      case e: Exception =>
        e.printStackTrace()
        Row(0)
    }
  }

}
