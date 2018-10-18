package com.demo.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * 操作hive表数据
  */
object HiveTableApp {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("HiveTableApp").master("local[2]").getOrCreate()
    //spark-shell运行
    spark.table("emp").show()

    spark.sqlContext.getConf("spark.sql.shuffle.partitions", "10")

    spark.sql("select deptno, count(1) as mount from emp group by deptno").filter("deptno is not null")
      .write.saveAsTable("emp_group_dept")

    spark.stop()
  }
}
