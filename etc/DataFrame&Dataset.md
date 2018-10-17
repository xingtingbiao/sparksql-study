DataFrame&Dataset

DataFrame产生背景                  DataFrame API操作案例实战
DataFrame概述                      DataFrame与RDD互操作之一
DataFrame对比RDD                   DataFrame与RDD互操作之二
DataFrame基本API常用操作           Dataset概述


DataFrame它不是SparkSQL提出的, 而是早在R、Pandas、Python语言中就已经有了.(都只能支持单机)

DataFrame概述：
1) A DataFrame is a Dataset organized into named columns.  以列(列名、类型、列值)的形式构成的分布式数据集, 安装列赋予不同的名称
A Dataset is a distributed collection of data.  分布式数据集
2)It is conceptually equivalent to a table in a relational database or a data frame in R/Python, but with richer optimizations under the hood.
3) RDD with schema



DataFrame对比RDD: 
RDD: 底层的调用不一样, 性能也就不一样
	java/scala ==> jvm
	python ==> python runtime

DataFrame:
	java/scala/python ==> Logic Plan(逻辑执行计划) 所以性能是一样的



DataFrame基本API常用操作: 
1) Create DataFrame
2) printSchema
3) show
4) select 
5) filter



DataFrame与RDD互操作: 
1) 反射方式: 反射到自定的case class  前提: 你要事先知道字段及其类型
	使用反射来推断包含了特定数据类型的RDD的元数据(对应的case class)
	可以使用DataFrame API编程方式或者SQL的方式
2) 编程方式: Row                     如果第一种不能满足你的要求(事先你不知道字段及其类型)
3) 优先选择第一种





DataFrame API操作案例实战: 
	学生信息统计案例




Dataset概述:
A Dataset is a distributed collection of data. 
DataFrame = Dataset[Row]
Dataset: 强类型 typed  case class
DataFrame: 弱类型   Row


SQL: 
	seletc name from person; compile OK, result NO  对于select,name都是运行时检查

DF: 
	df.select("name") 对于select compile检查, 对于name编译不检查，运行时检查

DS: 
	因为强类型(case class)所以语法和字段都不能写错，都是编译时期检查



