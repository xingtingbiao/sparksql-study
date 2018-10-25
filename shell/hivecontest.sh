#!/bin/bash

echo "hello world!"

spark-submit --class com.demo.spark.contextForSQL.HiveContextApp --master spark://hadoop001:7077 --jars /home/xingtb/software/mysql-connector-java-5.1.38.jar  /home/xingtb/lib/sql-1.0.jar 
