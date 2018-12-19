package com.demo.spark.log

import java.sql.{Connection, DriverManager, PreparedStatement}

/**
  * MySql工具类
  */
object MySqlUtil {

  /**
    * 获取数据库连接
    */
  def getConnection: Connection = {
    // val URL = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf8"
    val URL = "jdbc:mysql://192.168.111.129:3306/test?useUnicode=true&characterEncoding=utf8"
    val USERNAME = "admin"
    val PASSWORD = "admin"
    DriverManager.getConnection(URL, USERNAME, PASSWORD)
  }

  /**
    * 释放数据库资源
    */
  def release(conn: Connection, pstm: PreparedStatement): Unit = {
    try {
      if (null != pstm) pstm.close()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      if (null != conn) conn.close()
    }
  }

  def main(args: Array[String]): Unit = {
    println(getConnection)
  }
}
