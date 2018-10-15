Hive平滑过渡到SparkSQL

SQLContext/HiveContext/SparkSession的使用

spark-shell/spark-sql命令行的使用

thriftserver/beeline的使用

jdbc方式编程访问


提交Spark Application到环境中运行
./bin/spark-submit \
  --class <main-class>
  --master <master-url> \
  --name <application-name> \
  --deploy-mode <deploy-mode> \
  --conf <key>=<value> \
  ... # other options
  <application-jar> \
  [application-arguments]

  ./bin/spark-submit --class com.demo.spark.SQLContext.SQLContextApp --master spark://hadoop001:7077 /home/xingtb/lib/sql-1.0.jar /home/xingtb/lib/people.json


  HiveContext: 
  1) To use a HiveContext, you do not need to have an existing Hive setup, and all of the data sources available to a SQLContext are still available. HiveContext is only packaged separately to avoid including all of Hive’s dependencies in the default Spark build
  2) hive-site.xml

  SparkSession: 
  Spark2.x开始:
  	The entry point into all functionality in Spark is the SparkSession class. To create a basic SparkSession, just use SparkSession.builder():
  	import org.apache.spark.sql.SparkSession

	val spark = SparkSession
  		.builder()
  		.appName("Spark SQL basic example")
  		.config("spark.some.config.option", "some-value")
  		.getOrCreate()

  ./bin/spark-submit --class com.demo.spark.contextForSQL.SparkSessionApp --master spark://hadoop001:7077 /home/xingtb/lib/sql-1.0.jar /home/xingtb/lib/people.json



  spark-shell/spark-sql命令行的使用: 
  1) 需要将hive/conf目录下的hive-site.xml文件拷贝到spark/conf目录下
  2) 需要将mysql-connector-java-5.1.38.jar驱动包拷贝到spark/jars目录下

  spark-shell: 
    启动：./bin/spark-shell --master local[2]
  	执行：spark.sql("select * from emp e join dept d on e.deptno = d.deptno").show
  spark-sql: 
  	启动：./bin/spark-sql --master local[2]
  	执行：select * from emp e join dept d on e.deptno = d.deptno;

  	create table t(key string, value string);
  	explain extended select a.key*(2+3), b.value from t a join t b on a.key = b.key and a.key > 3;

== Parsed Logical Plan ==
'Project [unresolvedalias(('a.key * (2 + 3)), None), 'b.value]
+- 'Join Inner, (('a.key = 'b.key) && ('a.key > 3))
   :- 'UnresolvedRelation `t`, a
   +- 'UnresolvedRelation `t`, b

== Analyzed Logical Plan ==
(CAST(key AS DOUBLE) * CAST((2 + 3) AS DOUBLE)): double, value: string
Project [(cast(key#41 as double) * cast((2 + 3) as double)) AS (CAST(key AS DOUBLE) * CAST((2 + 3) AS DOUBLE))#45, value#44]
+- Join Inner, ((key#41 = key#43) && (cast(key#41 as double) > cast(3 as double)))
   :- SubqueryAlias a
   :  +- MetastoreRelation default, t
   +- SubqueryAlias b
      +- MetastoreRelation default, t

== Optimized Logical Plan ==
Project [(cast(key#41 as double) * 5.0) AS (CAST(key AS DOUBLE) * CAST((2 + 3) AS DOUBLE))#45, value#44]
+- Join Inner, (key#41 = key#43)
   :- Project [key#41]
   :  +- Filter (isnotnull(key#41) && (cast(key#41 as double) > 3.0))
   :     +- MetastoreRelation default, t
   +- Filter ((cast(key#43 as double) > 3.0) && isnotnull(key#43))
      +- MetastoreRelation default, t

== Physical Plan ==
*Project [(cast(key#41 as double) * 5.0) AS (CAST(key AS DOUBLE) * CAST((2 + 3) AS DOUBLE))#45, value#44]
+- *SortMergeJoin [key#41], [key#43], Inner
   :- *Sort [key#41 ASC NULLS FIRST], false, 0
   :  +- Exchange hashpartitioning(key#41, 200)
   :     +- *Filter (isnotnull(key#41) && (cast(key#41 as double) > 3.0))
   :        +- HiveTableScan [key#41], MetastoreRelation default, t
   +- *Sort [key#43 ASC NULLS FIRST], false, 0
      +- Exchange hashpartitioning(key#43, 200)
         +- *Filter ((cast(key#43 as double) > 3.0) && isnotnull(key#43))
            +- HiveTableScan [key#43, value#44], MetastoreRelation default, t





thriftserver/beeline的使用:
官网: The Thrift JDBC/ODBC server implemented here corresponds to the HiveServer2 in Hive 1.2.1 You can test the JDBC server with the beeline script that comes with either Spark or Hive 1.2.1.
即: thriftserver和hive里面的HiveServer2是一样的jdbc服务

thriftserver: 默认端口是10000(可修改)
    官网：
    ./sbin/start-thriftserver.sh \
    --hiveconf hive.server2.thrift.port=<listening-port> \
    --hiveconf hive.server2.thrift.bind.host=<listening-host> \
    --master <master-uri>
	启动: ./sbin/start-thriftserver.sh --master local[2]

beeline
	启动: ./bin/beeline -u jdbc:hive2://localhost:10000 -n xingtb


疑问? thriftserver/beeline模式和普通的spark-shell/spark-sql模式有什么区别？
1) spark-shell/spark-sql 都是启动一个客户端就生成一个spark application
1) 启动thriftserver后生成一个spark application 后面启动多少beeline客户端用的都是之前thriftserver对应的spark application
	解决了一个数据共享的问题, 多个beeline客户端可以共享数据.


