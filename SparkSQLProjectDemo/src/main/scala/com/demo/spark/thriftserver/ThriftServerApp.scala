package com.demo.spark.thriftserver

import java.sql.DriverManager

/**
  * 通过JDBC的方式访问
  */
object ThriftServerApp {

  def main(args: Array[String]): Unit = {
    Class.forName("org.apache.hive.jdbc.HiveDriver")
    val conn = DriverManager.getConnection("jdbc:hive2://hadoop001:10000", "xingtb", "")
    val ps = conn.prepareStatement("select empno, ename, sal from emp")
    val resultSet = ps.executeQuery()
    while (resultSet.next()) {
      println("empnp: " + resultSet.getInt("empno")
      + ", ename: " + resultSet.getString("ename")
        + ", sal: " + resultSet.getDouble("sal"))
    }

    if (null != resultSet) resultSet.close()
    if (null != ps) ps.close()
    if (null != conn) conn.close()
  }

}
