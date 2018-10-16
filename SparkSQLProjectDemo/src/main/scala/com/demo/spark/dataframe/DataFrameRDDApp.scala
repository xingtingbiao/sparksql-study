package com.demo.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * DataFrame与RDD的互操作
  */
object DataFrameRDDApp {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DataFrameRDDApp").master("local[2]").getOrCreate()
    val rdd = spark.sparkContext.textFile("people.txt")

    import spark.implicits._
    val frame = rdd.map(_.split(",")).map(line => Info(line(0).toInt, line(1), line(2).toInt)).toDF()
    frame.show()

    // 转换成表再写sql
//    frame.createOrReplaceTempView("table")
//    spark.sql("select * from table").show()

    spark.stop()
  }

  case class Info(id: Int, name: String, age: Int)
}
