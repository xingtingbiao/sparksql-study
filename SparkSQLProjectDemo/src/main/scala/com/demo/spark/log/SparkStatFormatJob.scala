package com.demo.spark.log

import com.demo.spark.utils.DateUtil
import org.apache.spark.sql.SparkSession

/**
  * 第一步: 抽取出我们所需列的数据
  */
object SparkStatFormatJob {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("SparkStatFormatJob").master("local[2]").getOrCreate()
    // val value = spark.sparkContext.textFile("../../testResource/access.20161111.log")
    val value = spark.sparkContext.textFile("/home/xingtb/lib/access.20161111.log")
    // value.take(10).foreach(println)
    value.map(line => {
      val splits = line.split(" ")
      val ip = splits(0)
      //TODO 这里的时间是3和4的拼接, 格式是[10/Nov/2016:00:01:02 +0800]
      val time = DateUtil.parse(splits(3) + " " + splits(4))
      val url = splits(11)
      val flow = splits(9)
      // (ip, time, url, flow)
      time + "\t" + url + "\t" + flow + "\t" + ip
    }).saveAsTextFile("/home/xingtb/lib/output/")

    spark.stop()
  }

}
