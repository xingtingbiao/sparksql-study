# sparksql-study

课程整套CDH相关软件下载地址：http://archive.cloudera.com/cdh5/cdh/5/
cdh5.7.0
生产环境或者测试环境选择对应的CDH版本，一定要采用尾号一样的版本

官网: http://hadoop.apache.org/
对于Apache的顶级项目来说， projectName.apache.org

Hadoop: http://hadoop.apache.org/
Hive: http://hive.apache.org/
Spark: http://spark.apache.org/

为什么很多公司选择Hadoop作为大数据平台的解决方案?:
1) 源码开源
2) 社区活跃、参与者很多  Spark
3) 涉及到分布式存储和计算的方方面面: 
	Flume进行数据的采集、
	Spark/MR/Hive等进行数据处理, 
	HDFS/HBase进行数据存储
4) 已经得到企业界的验证


HDFS架构

1 Master(NameNode/NN) 带N个Slaves(DataNode/DN)
HDFS/YARN/HBase

一个文件将被拆分成多个Block
blocksize: 128M
130M ==> 2个Block：128M 和 2M

NN：
1) 负责客户端请求的响应
2) 负责元数据(文件的名称、副本系数、Block存放的DN)的管理

DN：
1) 存储用户的文件对应的数据块(Block)
2) 要定期向NN发送心跳信息, 汇报本身及其所有Block信息, 健康状况

A typical deployment has a dedicated machine that runs only the NameNode software. 
Each of the other machines in the cluster runs one instance of the DataNode software
The architecture does not preclude running multiple DataNodes on the same machine but in a real deployment that is rarely the case.
NameNode + N个DataNode
建议NameNode 和 DataNode部署在不同的机器上

replication factor: 副本系数、副本因子

All blocks in a file except the last block are the same size