package com.demo.spark.log

import com.demo.spark.utils.AccessConvertUtil
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * 第二步: 使用Spark完成我们的数据清洗工作
  */
object SparkStatCleanJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[2]").appName("SparkStatCleanJob").getOrCreate()
    val rdd = spark.sparkContext.textFile("/home/xingtb/lib/access.log")

    // val rdd = spark.sparkContext.textFile("../../testResource/access.log")
    // rdd.take(10).foreach(println)
    // rdd ==> DF
    val row = rdd.map(line => AccessConvertUtil.parseLog(line))
    val frame = spark.createDataFrame(row, AccessConvertUtil.struct)
    frame.printSchema()
    frame.show()

    // coalesce()用于将小文件合并的, partitionBy(colNames: String*)根据字段分区的
    frame.coalesce(1).write.format("parquet").mode(SaveMode.Overwrite)
      .partitionBy("day").save("/home/xingtb/lib/clean")

    spark.stop()
  }

}
