package com.demo.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * Parquet文件操作
  */
object ParquetApp {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("ParquetApp").getOrCreate()
    val frame = spark.read.format("parquet").load("users.parquet")
    val frame2 = spark.read.format("parquet").option("path", "users.parquet").load()

    frame.printSchema()
    frame.show()
    frame2.show()

    // frame.select("name", "favorite_color").write.format("json").save()

    spark.stop()
  }
}
