package com.demo.spark.contextForSQL

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext

/**
  * SQLContext的使用
  * 注意: IDEA是在本地, 而测试数据是在服务器上, 能不能在本地进行开发测试呢?
  */
object SQLContextApp {

  def main(args: Array[String]): Unit = {
    val path = args(0)
    //1.创建相应的context
    val sparkConf = new SparkConf()
    // 在测试或者生产环境中, AppName和Master一般是用过脚本进行传值的
    // sparkConf.setAppName("SQLContextApp").setMaster("local[2]")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new SQLContext(sc)

    //2.相应的处理：json文件
    val people = sqlContext.read.format("json").load(path)
    people.printSchema()
    people.show()

    //3.关闭资源
    sc.stop()
  }
}
