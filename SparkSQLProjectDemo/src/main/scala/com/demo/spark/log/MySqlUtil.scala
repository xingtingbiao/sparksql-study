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
    DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8&useSSL=false&useUnicode=true&characterEncoding=utf8"
      , "admin", "admin")
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
