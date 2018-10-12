SparkSQL概述

1.Spark SQL前世今生                     4.Spark SQL愿景 

2.SQL on Hadoop常用框架                 5.Spark SQL架构

3.Spark SQL概述


1.Spark SQL前世今生: 
	为什么需要SQL
		1)事实上的标准
		2)易学易用
		3)受众面广

	Shark
		Hive: 类似于sql的Hive QL语言, sql ==> mapreduce
		特点: mapreduce的局限性直接影响了hive的性能(Hive底层依赖mapreduce)
		改进: hive on Tez, hive on Spark, hive on MapReduce
		hive on Spark ==> shark(新框架) 

		shark推出: 受欢迎, 基于spark, 基于内存的列式存储, 与hive能够兼容
		缺点: hive ql的解析、逻辑执行计划、执行计划的优化 都是依赖于hive的
		仅仅是将物理执行计划从MR任务转换成Spark任务

	Shark终止以后, 产生了2个分支:
		1) hive on spark 注意和上面的shark(hive on Spark)不同
			Hive社区, 源码是在Hive中
		2) Spark SQL
			Spark社区, 源码是在Spark中
			支持多种数据源、多种优化技术、扩展性好很多


2.SQL on Hadoop常用框架: 
1) Hive
	sql ==> mapreduce
	metastore  :  元数据
	sql: database, table, view
	facebook

2) impala
	cloudera: cdh(建议在生产环境上使用hadoop系列版本)、cm(web操作)
	sql: 自己的守护进程, 非MR, 基于内存, 对内存要求比较高
	metastore

3) presto
	facebook
	京东
	sql

4) drill
	sql
	访问: hdfs	rdbms	json	hbase	mangodb	  s3	hive

5) Spark SQL
	sql
	dataframe/dataset  api
	metastore
	访问: hdfs	rdbms	json	hbase	mangodb	  s3	hive  ==> 外部数据源



3.Spark SQL概述：
Part of zhe core distribution since Spark1.0(April 2014)
支持: Python Scala Java R

******
Spark SQL is Apache Spark's module for working with structured data.()

Spark SQL它不仅仅是有访问或者操作SQL的功能, 还提供了其他的丰富的操作: 外部数据源、优化

Spark SQL概述小结: 
1) Spark SQL 的应用并不局限于SQL
2) 可以访问hive、json、parquet等文件的数据
3) SQL只是Spark SQL的一个功能而已
===> Spark SQL这个名字对于它实际而言并不恰当
4) Spark SQL提供了SQL的API、Dataframe和Dataset的API






