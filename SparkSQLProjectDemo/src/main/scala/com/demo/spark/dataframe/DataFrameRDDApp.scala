package com.demo.spark.dataframe

import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
  * DataFrame与RDD的互操作
  */
object DataFrameRDDApp {


  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DataFrameRDDApp").master("local[2]").getOrCreate()
    // inferReflection(spark)
    program(spark)

    spark.stop()
  }

  /**
    * 通过case class反射的机制转换
    * @param spark
    */
  private def inferReflection(spark: SparkSession): Unit = {
    val rdd = spark.sparkContext.textFile("people.txt")
    import spark.implicits._
    val frame = rdd.map(_.split(",")).map(line => Info(line(0).toInt, line(1), line(2).toInt)).toDF()
    frame.show()

    // 转换成表再写sql
    //    frame.createOrReplaceTempView("table")
    //    spark.sql("select * from table").show()
  }

  /**
    * 通过编程的方式转换
    * @param spark
    */
  private def program(spark: SparkSession): Unit = {
    val rdd = spark.sparkContext.textFile("people.txt")
    val infoRDD = rdd.map(_.split(",")).map(line => Row(line(0).toInt, line(1), line(2).toInt))

    val structType = StructType(Array(StructField("id", IntegerType, nullable = true), StructField("name", StringType, nullable = true), StructField("age", IntegerType, nullable = true)))
    val frame = spark.createDataFrame(infoRDD, structType)
    frame.printSchema()
    frame.show()
  }

  case class Info(id: Int, name: String, age: Int)

}
