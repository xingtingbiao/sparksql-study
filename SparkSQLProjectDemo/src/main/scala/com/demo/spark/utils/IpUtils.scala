package com.demo.spark.utils

/**
  * ip解析的工具类
  */
object IpUtils {
  def getCity(ip: String): String = {
    IpHelper.findRegionByIp(ip)
  }

  def main(args: Array[String]): Unit = {
    println(getCity("58.30.15.255"))
  }
}
