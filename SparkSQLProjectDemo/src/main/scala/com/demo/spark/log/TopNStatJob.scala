package com.demo.spark.log

import org.apache.spark.sql.SparkSession

/**
  * TopN统计作业
  */
object TopNStatJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("TopNStatJob").master("local[2]").getOrCreate()
    val frame = spark.read.format("parquet").load("../../testResource/access-snappy.parquet")
    frame.printSchema()
    frame.show(false)
    spark.stop()
  }
}
