ExternalDataSourceAPI: 外部数据源API操作

课程目录：
	1.产生背景                          5.操作hive表数据
	2.概述                              6.操作MySQL表数据
	3.目标                              7.综合使用
	4.操作Parquet文件数据



1.产生背景：
	Every Spark application starts with loading data and ends with saving data.
	Loading and Saving Data is not easy.
	Parse raw data: text/json/parquet
	Convert data format transformation

	用户期望: 
		方便快速的从不同的数据源(json/parquet/rdbms), 经过混合处理(json join parquet), 再将处理结果以特定的格式(json/parquet)写回到指定的系统(HDFS/S3)上去.
		(补充: parquet/orc都是列式存储, 有时间可以好好看看其原理)

	Spark SQL 1.2 ==> 外部数据源 API



2.概述: 
	An extension way to integrate a various of external data sources into Spark SQL.
	Can read and write DataFrames using a variety of formats and storage systems.
	Data Sources API can automatically prune columns and push filters to source: Parquet/JDBC
	New API introduced in 1.2



3.目标: 
	Developer: build libraries for various data sources(不需要将开发的代码合并到Spark源码中, 可通过--jars传进去即可)

	User: easy loading/saving DataFrames
		spark.read.format("json")
			format
				build-in: json parquet jdbc  csv(2.x)
				packages: 外部的  不是spark内置的   https://spark-packages.org/




4.操作Parquet文件数据:
	spark.read.format("parquet").load(path)
	df.write.format("parquet").save(path)

	直接使用spark.read.load("users.parquet")没有指定format时为什么也可以？？
		看源码可知, 默认会有的一个format就是parquet


使用spark-sql:
	CREATE TEMPORARY VIEW parquetTable
	USING org.apache.spark.sql.parquet
	OPTIONS (
  	path "file:///home/xingtb/app/spark-2.1.0-bin-2.6.0-cdh5.7.0/examples/src/main/resources/users.parquet"
	)

	SELECT * FROM parquetTable




5.操作hive表数据:
	spark.table(tableName)
	df.write.saveAsTable(tableName)

	spark.sqlContext.getConf("spark.sql.shuffle.partitions", "10")
	在生产环境中一定要注意这个partitions的数量, 每一个RDD都是由多个partition构成





