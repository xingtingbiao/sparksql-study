Spark SQL务必要掌握的N件事情: 
    1) Spark SQL Use Cases(Spark SQL 使用案例)
    2) Loading data(加载数据)
    3) DataFrame Functions vs SQL(DataFrame 的函数和 SQL 的对比)
    4) Schemas(Schema信息)
    5) Loading & Saving Results(加载和保存结果)
    6) SQL Function Coverage(SQL 函数的覆盖面)
    7) Working with JSON(用到JSON的工作)
    8) External Data Sources(外部数据源)



1.Spark SQL Use Cases: 
    Ad-hoc querying of data in files
    Live SQL analytics over streaming data 
    ETL capabilities alongside familiar SQL
    Interaction with external Databases
    Scalable query performance with larger clusters


2.Loading data: 
    Load data directly into a DataFrame
    Load data into an RDD and transform it
    Can load data from local or Cloud


spark.sql("select * from parquet.`filePath`")  -- 直接通过sql的方式读取parquet文件


3.DataFrame Functions vs SQL
    DataFrame = RDD + Schema
    DataFrame is just a type alias for Dataset of Row -- Databricks
    DataFrame over RDD : Catalyst optimization&schemas
    DataFrame can handle : Text、JSON、Parquet and more
    Both SQL and API Functions in DF still Catalyst optimized



4. Schemas
    inferred(隐式): JSON  Parquet等类型的文件都有对应的字段名称, 所以可以直接推导出来的   

    explicit(显式)



5. Loading & Saving Results
    Loading and Saving is fairly straight forward
    Save your dataframes in your desired format



6. Working with JSON
    JSON data is most easily read-in as line delimied
    Schema is inferred upon load
    if you want to flatten your JSON data, use zhe explode method  --explode(array) 将查询字段类型为数组的数据压平
    Access nested-objects with dot syntax   



7. SQL Function Coverage
    2.0版本的Spark已经支持 SQL 2003的标准了
    Runs all 99 of TPS-DS benchmark queries
    SubQuery supports  -- 支持子查询
    vectorization   -- 支持向量化



8. External Data Sources(spark-packages.org  --***外部数据源的所有支持的包的网站)
    rdbms, need JDBC jars
    Parquet、Phoenix、csv、avro、etc


