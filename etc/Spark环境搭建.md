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