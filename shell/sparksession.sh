#!/bin/bash

echo "hello world!"

spark-submit --class com.demo.spark.contextForSQL.SparkSessionApp --master spark://hadoop001:7077 /home/xingtb/lib/sql-1.0.jar /home/xingtb/lib/people.json
