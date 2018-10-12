Spark实战环境搭建
1) Spark源码编译
2) Spark环境搭建

下载源码包: wget https://archive.apache.org/dist/spark/spark-2.1.0/spark-2.1.0.tgz

下载maven: wget http://mirrors.shu.edu.cn/apache/maven/maven-3/3.5.4/binaries/apache-maven-3.5.4-bin.tar.gz

前置要求:
1) Building Spark using Maven requires Maven 3.3.9 or newer and Java 7+

2) You’ll need to configure Maven to use more memory than usual by setting MAVEN_OPTS:
export MAVEN_OPTS="-Xmx2g -XX:ReservedCodeCacheSize=512m"

mvn编译命令:
./build/mvn -Pyarn -Phadoop-2.4 -Dhadoop.version=2.4.0 -DskipTests clean package

<properties>
    <maven.version>3.3.9</maven.version>
    <hadoop.version>2.2.0</hadoop.version>
    <yarn.version>${hadoop.version}</yarn.version>
</properties>

<profile>
      <id>hadoop-2.7</id>
      <properties>
        <hadoop.version>2.7.3</hadoop.version>
        <jets3t.version>0.9.3</jets3t.version>
        <zookeeper.version>3.4.6</zookeeper.version>
        <curator.version>2.6.0</curator.version>
      </properties>
</profile>

<profile>
      <id>yarn</id>
      <modules>
        <module>yarn</module>
        <module>common/network-yarn</module>
      </modules>
</profile>


./build/mvn -Pyarn -Phadoop-2.6 -Dhadoop.version=2.6.0-cdh5.7.0 -Phive -Phive-thriftserver -DskipTests clean package

Building a Runnable Distribution(编译可运行的包):
推荐使用:
./dev/make-distribution.sh --mvn /home/xingtb/app/maven/apache-maven-3.5.4/bin/mvn --name 2.6.0-cdh5.7.0 --tgz -Pyarn -Phadoop-2.6 -Dhadoop.version=2.6.0-cdh5.7.0 -Phive -Phive-thriftserver

cdh需要depen repos 可以在pom中尝试添加

<repository>
      <id>cloudera-releases</id>
      <url>https://repository.cloudera.com/artifactory/cloudera-repos</url>
      <releases>
        <enabled>true</enabled>
      </releases>
      <snapshots>
       <enabled>false</enabled>
      </snapshots>
</repository>

网络不好的话可以使用VPN

--------------------------------------------------------------------------------------------------------------------------

Spark Standalone模式的架构和Hadoop HDFS/YARN很类似
1 master + n worker

spark-env.sh配置: 
SPARK_MASTER_HOST=hadoop001
SPARK_WORKER_CORES=2
SPARK_WORKER_MEMORY=2g
SPARK_WORKER_INSTANCES=1

hadoop1 : master
hadoop2 : worker
hadoop3 : worker
hadoop4 : worker
...
hadoop10 : worker

slaves:
hadoop2
hadoop3
hadoop4
...
hadoop10

==> start-all.sh  会在hadoop1机器上启动master进程, 在slaves文件配置的所有hostname机器上启动worker进程



Spark简单使用
val file = spark.sparkContext.textFile("file:///home/xingtb/data/wc.txt")
val wordCounts = file.flatMap(line => line.split(",")).map(word => (word, 1)).reduceByKey(_ + _)
wordCounts.collect

scala> wordCounts.collect
res0: Array[(String, Int)] = Array((hello,3), (welcome,1), (world,2))