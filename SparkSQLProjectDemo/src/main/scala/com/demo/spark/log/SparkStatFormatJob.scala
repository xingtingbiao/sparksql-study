package com.demo.spark.log

import org.apache.spark.sql.SparkSession

/**
  * 第一步: 抽取出我们所需列的数据
  */
object SparkStatFormatJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SparkStatFormatJob").master("local[2]").getOrCreate()
    val value = spark.sparkContext.textFile("../../testResource/access.20161111.log")
    value.take(10).foreach(println)


    spark.stop()
  }

}
