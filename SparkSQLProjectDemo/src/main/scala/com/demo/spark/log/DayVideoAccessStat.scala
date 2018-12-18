package com.demo.spark.log

case class DayVideoAccessStat(day: String, cmsId: Long, times: Long)

case class DayCityVideoAccessStat(day: String, city: String, cmsId: Long, times: Long, timesRank: Int)

case class DayFlowVideoAccessStat(day: String, cmsId: Long, flows: Long)
