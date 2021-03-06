Spark on YARN

1. 概述
    Spark支持可插拔的集群管理模式

在Spark中, 支持4种运行模式
1) Local: 开发时使用
2) Standalone: Spark自带的, 如果一个集群是Standalone的话, 那么就需要在多台及其上同时部署Spark环境
3) YARN: 建议在生产环境上使用该模式, 统一使用YARN进行整个集群作业的(MR、Spark)的资源调度
4) Mesos

注意: 不管使用什么模式, Spark应用程序的代码都是一模一样的, 只需要在提交的时候通过--master参数指定我们的运行模式即可

2.模式
1)Spark on YARN之client模式
    Driver运行在Client端(提交Spark作业的机器)
    Client会和请求到的Container进行通信来完成作业的调度和执行, Client是不能退出的
    日志信息会在控制台输出：便于我们测试

2)Spark on YARN之cluster模式
    Driver运行在ApplicationMaster中
    Client只要提交完作业就可以关掉了, 因为作业已经在YARN上运行了
    日志信息不会在终端显示, 因为日志在Driver上, 只能通过yarn logs -applicationId <app ID> 命令查看


两种模式的对比: 
	1) Driver的运行位置
	2) ApplicationMaster的职责
	3) 运行输出日志的位置

For example:
./bin/spark-submit --class org.apache.spark.examples.SparkPi \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 4g \
    --executor-memory 2g \
    --executor-cores 1 \
    --queue thequeue \
    examples/jars/spark-examples*.jar \
    10

注意：此处的yarn就是我们的yarn client 模式
如要要用yarn cluster模式的话, 加上--deploy-mode cluster 参数即可

spark-submit --class org.apache.spark.examples.SparkPi \
    --master yarn \
    --driver-memory 2g \
    --executor-memory 1g \
    --executor-cores 1 \
    --queue thequeue \
    /home/xingtb/app/spark-2.1.0-bin-2.6.0-cdh5.7.0/examples/jars/spark-examples*.jar \
    4

 spark-submit --class org.apache.spark.examples.SparkPi \
    --master yarn \
    --deploy-mode cluster \
    --driver-memory 2g \
    --executor-memory 1g \
    --executor-cores 1 \
    --queue thequeue \
    /home/xingtb/app/spark-2.1.0-bin-2.6.0-cdh5.7.0/examples/jars/spark-examples*.jar \
    4

 Exception in thread "main" java.lang.Exception: When running with master 'yarn' either HADOOP_CONF_DIR or YARN_CONF_DIR must be set in the environment.

 如果想运行在YARN之上, 那么就必须要设置HADOOP_CONF_DIR或者YARN_CONF_DIR参数的值
 1) export HADOOP_CONF_DIR=/home/xingtb/app/hadoop-2.6.0-cdh5.7.0/etc/hadoop
 2) $SPARK_HOME/conf/spark-env.sh


yarn上执行我们的作业

mvn assembly:assembly -Dmaven.test.skip=true 打包命令

spark-submit --class com.demo.spark.log.SparkStatCleanJobYARN \
--master yarn \
--name SparkStatCleanJobYARN \
--deploy-mode cluster \
--driver-memory 2g \
--executor-memory 1g \
--executor-cores 1 \
--queue thequeue \
/home/xingtb/lib/sql-1.0-jar-with-dependencies.jar \
hdfs://hadoop001:8020/spark/input/* hdfs://hadoop001:8020/spark/clean


spark-submit --class com.demo.spark.log.TopNStatJobYARN \
--master yarn \
--name TopNStatJobYARN \
--deploy-mode cluster \
--driver-memory 2g \
--executor-memory 1g \
--executor-cores 1 \
--queue thequeue \
/home/xingtb/lib/sql-1.0-jar-with-dependencies.jar \
hdfs://hadoop001:8020/spark/clean/ 20170511
