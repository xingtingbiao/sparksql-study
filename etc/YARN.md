

YARN架构:
1 RM(ResourceManager) + N NM(NodeManager)

ResourceManager的职责: 一个集群active状态的RM只有一个, 负责整个资源的管理和调度.
1) 处理客户端的请求(启动/杀死 作业)
2) 启动/监控ApplicationMaster(一个作业对应一个AM)
3) 监控NM
4) 系统资源的分配和调度

NodeManager的职责: 整个集群中有N个, 负责单个节点的资源管理和使用以及Task的运行情况
1) 定期向RM汇报本节点的资源使用请求和各个Container的运行情况
2) 接收并处理RM的Container启停的各种命令
3) 单个节点的资源管理和任务管理


ApplicationMaster: 每个应用/作业 对应一个, 负责应用程序的管理
1) 数据的切分
2) 为应用程序向RM申请资源(container), 并分配给内部任务
3) 与NM进行通信以启停task, task是运行在container中的
4) task的监控和容错


Container: 对任务运行情况的一个描述(cpu、memory、环境变量等等)


YARN执行流程
1) 用户向YARN提交作业
2) RM向该作业分配第一个container(AM)
3) RM会与对应的NM通信, 要求NM在这个container上启动一个应用程序AM
4) AM首先向RM注册, 然后AM将为各个任务申请资源, 并监控运行情况
5) AM采用轮询的方式通过RPC协议向RM申请和领取资源
6) AM申请到资源以后, 便和相应的NM通信, 要求NM启动任务
7) NM启动我们作业对应的task


YARN环境搭建
1) mapred-site.xml
	<property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>
2) yarn-site.xml
    <property>
        <name>mapreduce.framework.name</name>
        <value>yarn</value>
    </property>


启动YARN:
	sbin/start-yarn.sh
	验证是否启动成功: 
	jps:
		13011 NodeManager
		12884 ResourceManager
	web: 
		http://hadoop001:8088/


停止YARN:
	sbin/stop-yarn.sh



提交一个MR作业到YARN上运行
	官方提供的jar:
		/home/xingtb/app/hadoop-2.6.0-cdh5.7.0/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.6.0-cdh5.7.0.jar
		hadoop jar hadoop-mapreduce-examples-2.6.0-cdh5.7.0.jar wordcount /input/wc/hello.txt /output/wc/

		当我们重复执行该作业时会报错: org.apache.hadoop.mapred.FileAlreadyExistsException: Output directory hdfs://hadoop001:8020/output/wc already exists
