package com.demo.spark.log

import java.sql.{Connection, PreparedStatement}

import scala.collection.mutable.ListBuffer

/**
  * 各个统计纬度的Dao操作
  */
object StatDao {

  def insertDayVideoAccessTopN(list: ListBuffer[DayVideoAccessStat]): Unit = {
    var conn: Connection = null
    var pstm: PreparedStatement = null
    try {
      conn = MySqlUtil.getConnection
      conn.setAutoCommit(false)
      val sql = "insert into day_video_access_topn_stat(day, cms_id, times) values(?, ?, ?)"
      pstm = conn.prepareStatement(sql)
      for (dvas <- list) {
        pstm.setString(1, dvas.day)
        pstm.setLong(2, dvas.cmsId)
        pstm.setLong(3, dvas.times)
        pstm.addBatch()
      }
      pstm.executeBatch()
      conn.commit()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      MySqlUtil.release(conn, pstm)
    }
  }
}
