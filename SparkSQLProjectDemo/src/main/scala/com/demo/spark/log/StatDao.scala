package com.demo.spark.log

import java.sql.{Connection, PreparedStatement}

import scala.collection.mutable.ListBuffer

/**
  * 各个统计纬度的Dao操作
  */
object StatDao {

  def deleteData(day: String): Unit = {
    var conn: Connection = null
    var pstm: PreparedStatement = null
    val tables = Array("day_video_access_topn_stat", "day_video_city_access_topn_stat", "day_video_flow_access_topn_stat")
    try {
      conn = MySqlUtil.getConnection
      for (tab <- tables) {
        val sql = s"delete from $tab where day = ?"
        pstm = conn.prepareStatement(sql)
        pstm.setString(1, day)
        pstm.executeUpdate()
      }
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      MySqlUtil.release(conn, pstm)
    }
  }

  def insertCityDayVideoAccessTopN(list: ListBuffer[DayCityVideoAccessStat]): Unit = {
    var conn: Connection = null
    var pstm: PreparedStatement = null
    try {
      conn = MySqlUtil.getConnection
      conn.setAutoCommit(false)
      val sql = "insert into day_video_city_access_topn_stat(day, city, cms_id, times, times_rank) values(?, ?, ?, ?, ?)"
      pstm = conn.prepareStatement(sql)
      for (dvas <- list) {
        pstm.setString(1, dvas.day)
        pstm.setString(2, dvas.city)
        pstm.setLong(3, dvas.cmsId)
        pstm.setLong(4, dvas.times)
        pstm.setLong(5, dvas.timesRank)
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

  def insertDayFlowVideoAccessTopN(list: ListBuffer[DayFlowVideoAccessStat]): Unit = {
    var conn: Connection = null
    var pstm: PreparedStatement = null
    try {
      conn = MySqlUtil.getConnection
      conn.setAutoCommit(false)
      val sql = "insert into day_video_flow_access_topn_stat(day, cms_id, flows) values(?, ?, ?)"
      pstm = conn.prepareStatement(sql)
      for (dvas <- list) {
        pstm.setString(1, dvas.day)
        pstm.setLong(2, dvas.cmsId)
        pstm.setLong(3, dvas.flows)
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
