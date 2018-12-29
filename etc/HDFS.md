#HDFS 环境搭建
使用版本: hadoop-2.6.0-cdh5.7.0

xingtb/xingtb
home/xingtb/
			software：软件存放的目录
			app：软件安装目录
			data：测试数据
			source: 存放的是源码：spark


Hadoop环境搭建
1) 下载Hadoop
	wget http://archive.cloudera.com/cdh5/cdh/5/hadoop-2.6.0-cdh5.7.0.tar.gz
	
2) 安装JDK
	下载
	解压到app目录：tar -xzvf jdk-8u181-linux-x64.tar.gz -C ~/app/
	验证是否安装成功：~/app/jdk1.8.0_181/bin/  ./java -version
	把bin目录配置到系统环境变量中(~/.bash_profile)
	export JAVA_HOME=/home/xingtb/app/jdk1.8.0_181
	export PATH=$JAVA_HOME/bin:$PATH

3) 机器参数设置
	hostname: hadoop001
	1) 修改主机名
	2) 修改IP和hostname的映射关系：/etc/hosts
		192.168.174.140 hadoop001
	3) ssh 免密码登录
		ssh-keygen -t rsa
		cp ~/.ssh/id_rsa.pub ~/.ssh/authorized_keys
		注意对应用户名下的.ssh
		
4) Hadoop配置文件修改: ~/app/hadoop-2.6.0-cdh5.7.0/etc/hadoop/
	hadoop-env.sh
		export JAVA_HOME=/home/xingtb/app/jdk1.8.0_181
	
	core-site.xml
		<property>
				<name>fs.defaultFS</name>
				<value>hdfs://hadoop001:8020</value>
		</property>
		
		<property>
				<name>hadoop.tmp.dir</name>
				<value>/home/xingtb/app/tmp</value>
		</property>
	
	
	hdfs-site.xml
		<property>
			<name>dfs.replication</name>
			<value>1</value>
		</property>
		
5) 格式化HDFS
	注意: 这一步操作只在第一次执行, 此后无需再执行, 如果每次都格式化, 数据将被清空！
	bin/hdfs namenode -format
	
6) 启动HDFS
	sbin/start-dfs.sh
	
	验证是否成功: 
		jps:
			DataNode
			NameNode
			SecondaryNameNode
		浏览器：hadoop001:50070
			

	
7) 目录描述：
	bin/: 客户端执行脚本
	sbin/: 服务器端执行脚本
	etc/: 相关配置文件
	
8) 停止HDFS服务