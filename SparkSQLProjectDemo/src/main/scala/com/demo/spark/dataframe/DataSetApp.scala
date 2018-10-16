package com.demo.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * DataSet API 操作
  */
object DataSetApp {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DataSetApp").master("local[2]").getOrCreate()

    // 转DataSet也需要隐式转换
    import spark.implicits._
    // spark 如何读取csv文件
    val frame = spark.read.option("header", "true").option("inferSchema", "true").csv("sales.csv")
    frame.show()

    val sales = frame.as[Sale]
    sales.map(x => x.itemId).show()

    spark.stop()
  }

  case class Sale(transactionId: Int, customerId: Int, itemId: Int, amountPaid: Double)

}
