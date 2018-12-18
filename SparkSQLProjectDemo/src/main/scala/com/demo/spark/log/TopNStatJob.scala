package com.demo.spark.log

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
    // frame.show(false)

    videoAccessTopNStat(spark, frame)

    spark.stop()
  }

  def videoAccessTopNStat(spark: SparkSession, frame: DataFrame): Unit = {
    /**
      * DataFrame API 的方式
      */
//    import spark.implicits._
//    val videoAccess = frame.filter($"day" === "20170511" && $"cmsType" === "video")
//      .groupBy("day", "cmsId").agg(count("cmsId").as("times")).orderBy($"times".desc)
//    videoAccess.show(false)

    /**
      * SQL的方式
      */
    frame.createOrReplaceTempView("video_access")
    val videoAccess = spark.sql("select day, cmsId, count(1) as times from video_access where day = '20170511' and cmsType = 'video' group by day, cmsId order by times desc")
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
}
