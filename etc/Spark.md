Spark产生背景

描述:
	Lightning-fast unified analytics engine(快如闪电的计算框架)

Spark概述及特点
	Speed: 快速(Hadoop基于进程模型, Spark基于线程模型并且线程还有线程池减少资源的开销)
	Easy of Use: 易用(Write applications quickly in Java, Scala, Python, R, and SQL.)
	Generality: 通用(Combine SQL, streaming, and complex analytics.)
	Runs Everywhere: Spark runs on Hadoop(YARN), Apache Mesos, Kubernetes, standalone, or in the cloud. It can access diverse data sources.


MapReduce局限性
1) 代码繁琐
2) 只支持map和reduce方法
3) 执行效率低下
4) 不适合迭代多次、交互式、流式的处理


框架的多样化
1) 批处理(离线): MapReduce, Hive, Pig
2) 流式处理(实时): Storm, JStorm
3) 交互式计算: Impala

劣势: 学习、运维成本大大的提升

===> Spark同时完成框架多样化



Spark对比Hadoop: BDAS
	BDAS: Berkeley Data Analytics Stack




Spark实战环境搭建
1) Spark源码编译
2) Spark环境搭建