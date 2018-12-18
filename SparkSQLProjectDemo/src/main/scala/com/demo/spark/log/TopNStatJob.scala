package com.demo.spark.log

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

import scala.collection.mutable.ListBuffer

/**
  * TopN统计作业
  */
object TopNStatJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("TopNStatJob")
      .config("spark.sql.sources.partitionColumnTypeInference.enabled", value = false)
      .master("local[2]").getOrCreate()
    val frame = spark.read.format("parquet").load("../../testResource/clean")
    frame.printSchema()
    frame.show(false)

    // 初始化话删除指定天的所有数据
    val day = "20170511"
    StatDao.deleteData(day)

    // 最受欢迎的TopN课程
     videoAccessTopNStat(spark, frame, day)

    // 按地市进行统计TopN课程
     cityAccessTopNStat(spark, frame, day)

    // 按流量flow统计最受欢迎的课程
    flowAccessTopNStat(spark, frame, day)
    spark.stop()
  }

  /**
    * 最受欢迎课程统计的结果并插入到数据库
    */
  def videoAccessTopNStat(spark: SparkSession, frame: DataFrame, day: String): Unit = {
    /**
      * DataFrame API 的方式
      */
    import spark.implicits._
    val videoAccess = frame.filter($"day" === day && $"cmsType" === "video")
      .groupBy("day", "cmsId").agg(count("cmsId").as("times")).orderBy($"times".desc)
//    videoAccess.show(false)

    /**
      * SQL的方式
      */
//    frame.createOrReplaceTempView("video_access")
//    val videoAccess = spark.sql("select day, cmsId, count(1) as times from video_access where day = '20170511' and cmsType = 'video' group by day, cmsId order by times desc")
    // videoAccess.show(false)
    /**
      * 插入数据库操作
      */
    try {
      videoAccess.foreachPartition(partitionOfRecords => {
        val list: ListBuffer[DayVideoAccessStat] = new ListBuffer[DayVideoAccessStat]
        partitionOfRecords.foreach(info => {
          val day = info.getAs[String]("day")
          val cmsId = info.getAs[Long]("cmsId")
          val times = info.getAs[Long]("times")
          list.append(DayVideoAccessStat(day, cmsId, times))
        })
        StatDao.insertDayVideoAccessTopN(list)
      })
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 按地区统计的结果并插入到数据库
    */
  def cityAccessTopNStat(spark: SparkSession, frame: DataFrame, day: String): Unit = {
    import spark.implicits._
    val cityDF = frame.filter($"day" === day && $"cmsType" === "video")
      .groupBy("day", "city", "cmsId")
      .agg(count("cmsId").as("times")).orderBy($"times".desc)
    // Window 函数
    val city3DF = cityDF.select(cityDF("day"), cityDF("city"), cityDF("cmsId"), cityDF("times")
      , row_number().over(Window.partitionBy(cityDF("city")).orderBy(cityDF("times").desc))
        .as("times_rank")
    ).filter($"times_rank" <= 3)
      // .show(false)
    try {
      city3DF.foreachPartition(partitionOfRecords => {
        val list: ListBuffer[DayCityVideoAccessStat] = new ListBuffer[DayCityVideoAccessStat]
        partitionOfRecords.foreach(info => {
          val day = info.getAs[String]("day")
          val city = info.getAs[String]("city")
          val cmsId = info.getAs[Long]("cmsId")
          val times = info.getAs[Long]("times")
          val timesRank = info.getAs[Int]("times_rank")
          list.append(DayCityVideoAccessStat(day, city, cmsId, times, timesRank))
        })
        StatDao.insertCityDayVideoAccessTopN(list)
      })
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }

  /**
    * 按流量统计的结果并插入到数据库
    */
  def flowAccessTopNStat(spark: SparkSession, frame: DataFrame, day: String): Unit = {
    import spark.implicits._
    val flowDF = frame.filter($"day" === day && $"cmsType" === "video")
      .groupBy("day", "cmsId")
      .agg(sum("flow").as("flows")).orderBy($"flows".desc)
     // flowDF.show(false)
    try {
      flowDF.foreachPartition(partitionOfRecords => {
        val list: ListBuffer[DayFlowVideoAccessStat] = new ListBuffer[DayFlowVideoAccessStat]
        partitionOfRecords.foreach(info => {
          val day = info.getAs[String]("day")
          val cmsId = info.getAs[Long]("cmsId")
          val flows = info.getAs[Long]("flows")
          list.append(DayFlowVideoAccessStat(day, cmsId, flows))
        })
        StatDao.insertDayFlowVideoAccessTopN(list)
      })
    } catch {
      case e: Exception => e.printStackTrace()
    }
  }
}
