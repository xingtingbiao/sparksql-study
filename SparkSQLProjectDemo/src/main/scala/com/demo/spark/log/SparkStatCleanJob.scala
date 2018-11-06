package com.demo.spark.log

import com.demo.spark.utils.AccessConvertUtil
import org.apache.spark.sql.SparkSession

/**
  * 第二步: 使用Spark完成我们的数据清洗工作
  */
object SparkStatCleanJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SparkStatCleanJob").master("local[2]").getOrCreate()
    val rdd = spark.sparkContext.textFile("../../testResource/access.log")
    // rdd.take(10).foreach(println)
    // rdd ==> DF
    val row = rdd.map(line => AccessConvertUtil.parseLog(line))
    val frame = spark.createDataFrame(row, AccessConvertUtil.struct)
    frame.printSchema()
    frame.show()

    spark.stop()
  }

}
