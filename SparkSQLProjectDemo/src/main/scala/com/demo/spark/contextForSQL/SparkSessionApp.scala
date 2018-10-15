package com.demo.spark.contextForSQL

import org.apache.spark.sql.SparkSession

/**
  * SparkSession的使用
  */
object SparkSessionApp {

  def main(args: Array[String]): Unit = {
    // val path = args(0)
    val spark = SparkSession.builder().appName("SparkSessionApp").master("local[2]").getOrCreate()

    val people = spark.read.json("people.json")
    people.show()

    spark.stop()
  }

}
