package com.demo.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * DataFrame API基本操作
  */
object DataFrameApp {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DataFrameApp").master("local[2]").getOrCreate()
    val frame = spark.read.format("json").load("people.json")
    // 打印schema信息
    frame.printSchema()
    // 展示默认20条数据
    frame.show()
    // 展示name这一列的数据：select name from table
    frame.select("name").show()

    // select name, age + 10 as newAge from table
    frame.select(frame.col("name"), (frame.col("age") + 10).alias("newAge")).show()

    // select name, age from table where age > 19
    frame.select("name", "age").filter(frame.col("age") > 19).show()

    // select age ,count(1) from table group by age
    frame.groupBy("age").count().show()

    spark.stop()
  }

}
