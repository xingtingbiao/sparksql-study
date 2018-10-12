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