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

	用户期望: 
		方便快速的从不同的数据源(json/parquet/rdbms), 经过混合处理(json join parquet), 再将处理结果以特定的格式(json/parquet)写回到指定的系统(HDFS/S3)上去.
		(补充: parquet/orc都是列式存储, 有时间可以好好看看其原理)

	Spark SQL 1.2 ==> 外部数据源 API