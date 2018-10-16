package com.demo.spark.dataframe

import org.apache.spark.sql.SparkSession

/**
  * DataFrame API 案例操作
  */
object DataFrameCase {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("DataFrameCase").master("local[2]").getOrCreate()
    val rdd = spark.sparkContext.textFile("student.data")
    import spark.implicits._
    val frame = rdd.map(_.split("\\|")).map(line => Student(line(0).toInt, line(1), line(2), line(3))).toDF()
    frame.show(30, truncate = false)
    frame.take(10)
    frame.first()
    frame.head(3)

    // filter API
    frame.filter("name = '' or name = 'NULL'").show()
    frame.filter("substr(name, 0, 1) = 'M'").show()

    // sort API
    frame.sort(frame("name")).show()
    frame.sort(frame("name").desc).show()
    frame.sort("name", "id").show()
    frame.sort(frame("name"), frame("id").desc).show()

    val frame2 = rdd.map(_.split("\\|")).map(line => Student(line(0).toInt, line(1), line(2), line(3))).toDF()

    // join API
    frame.join(frame2, frame("id") === frame2("id")).show()

    spark.stop()
  }

  case class Student(id: Int, name: String, phone: String, email: String)

}
