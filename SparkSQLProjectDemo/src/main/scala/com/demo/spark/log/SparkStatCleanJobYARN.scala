package com.demo.spark.log

import com.demo.spark.utils.AccessConvertUtil
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * 第二步: 使用Spark完成我们的数据清洗工作
  */
object SparkStatCleanJobYARN {

  def main(args: Array[String]): Unit = {
    if (args.length != 2) {
      println("Usage: SparkStatCleanJobYARN <inputPath> <outputPath>")
      System.exit(1)
    }
    val Array(inputPath, outputPath) = args
    val spark = SparkSession.builder().getOrCreate()
    val rdd = spark.sparkContext.textFile(inputPath)
    // rdd ==> DF
    val row = rdd.map(line => AccessConvertUtil.parseLog(line))
    val frame = spark.createDataFrame(row, AccessConvertUtil.struct)
    frame.printSchema()
    frame.show()

    // coalesce()用于将小文件合并的, partitionBy(colNames: String*)根据字段分区的
    frame.coalesce(1).write.format("parquet").mode(SaveMode.Overwrite)
      .partitionBy("day").save(outputPath)

    spark.stop()
  }

}
