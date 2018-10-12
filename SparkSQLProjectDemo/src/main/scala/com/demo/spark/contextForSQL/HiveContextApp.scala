package com.demo.spark.contextForSQL

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.hive.HiveContext

/**
  * HiveContext的使用
  * 使用是需要通过--jars 将MySQL的驱动jar包传递进去
  */
object HiveContextApp {

  def main(args: Array[String]): Unit = {
    //1.创建相应的context
    val sparkConf = new SparkConf()
    // 在测试或者生产环境中, AppName和Master一般是用过脚本进行传值的
    // sparkConf.setAppName("HiveContextApp").setMaster("local[2]")
    val sc = new SparkContext(sparkConf)
    val hiveContext = new HiveContext(sc)

    //2.相应的处理
    hiveContext.table("emp").show()

    //3.关闭资源
    sc.stop()
  }
}
