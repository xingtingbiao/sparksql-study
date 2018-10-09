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
	
	
centos7安装MySQL：https://blog.csdn.net/pengjunlee/article/details/81212250
				  https://www.cnblogs.com/jorzy/p/8455519.html

Hive 下载:
	wget http://archive.cloudera.com/cdh5/cdh/5/hive-1.1.0-cdh5.7.0.tar.gz
	解压: tar -xzvf hive-1.1.0-cdh5.7.0.tar.gz -C ~/app/

配置
1) 设置系统环境变量: 
	export HIVE_HOME=/home/xingtb/app/hive-1.1.0-cdh5.7.0
    export PATH=$HIVE_HOME/bin:$PATH

    新建hive-site.xml

    <property>
        <name>javax.jdo.option.ConnectionURL</name>
        <value>jdbc:mysql://localhost/sparksql?createDatabaseIfNotExist=true</value>
    </property>

    <property>
        <name>javax.jdo.option.ConnectionDriverName</name>
        <value>com.mysql.jdbc.Driver</value>
    </property>

    <property>
        <name>javax.jdo.option.ConnectionUserName</name>
        <value>root</value>
    </property>

    <property>
        <name>javax.jdo.option.ConnectionPassword</name>
        <value>root</value>
    </property>


Hive基本使用
	创建表
		create table hive_wordcount(context string);
	加载数据
		LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename [PARTITION (partcol1=val1, partcol2=val2 ...)]
		load data local inpath '/home/xingtb/data/hello.txt' into table hive_wordcount;

		select word, count(1) from hive_wordcount lateral view explode(split(context, '\t')) wc AS word group by word;
		lateral view explode(): 把每行记录按照指定分隔符进行拆解

	hive sql 提交执行以后会生成mr作业, 并在yarn上运行

简单案例
	员工表和部门表的操作
	