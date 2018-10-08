大数据数据仓库Hive

Hive产生的背景
	MapReduce编程的不方便
	HDFS上的文件缺少Schema


Hive底层的执行引擎
	Hive on MapReduce
	Hive on Tez
	Hive on Spark

Hive支持多种不同的压缩格式、存储格式以及自定义函数
	压缩: GZIP/LZO/Snappy/BZIP2...
	存储: TextFile/SequenceFile/RCFile/ORC/Parquet
	UDF: 自定义函数(User Define Function)

Hive为什么使用:
	简单、容易上手(提供类似SQL的查询语言HQL)
	为超大数据集设计的计算/存储扩展能力(MR计算, HDFS存储)
	统一的元数据管理(可与Presto/Impala/SparkSQL等共享数据)   ********重点*********