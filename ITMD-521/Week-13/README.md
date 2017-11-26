# SPARK

# Assumptions
    
    - Run in java enviroment
    - SPARK downloaded from http://apache.claz.org/spark/spark-2.2.0/spark-2.2.0-bin-hadoop2.7.tgz
    - Run the following command to unzip the downloaded tar:
         tar -xvf spark-2.2.0-bin-hadoop2.7.tar.gz
    - Configured information of ./bashrc file looks like this or something else depending on the location sqoop file has been placed:
    
        export JAVA_HOME=/usr
        export HADOOP_HOME=/home/vagrant/hadoop-2.6.5
        export SQOOP_HOME=/home/vagrant/sqoop
        export PATH=$PATH:~/hadoop-2.6.5/bin:~/hadoop-2.6.5/sbin:$HADOOP_HOME/bin:$SQOOP_HOME/bin:$SPARK_HOME/bin
        export HADOOP_CLASSPATH=/usr/lib/jvm/java-8-openjdk-amd64/lib/tools.jar:/home/vagrant/sqoop/sqoop-1.4.6.jar
        export CLASSPATH=$CLASSPATH:/usr/share/java/mysql.jar
        export SPARK_HOME=/home/vagrant/spark
        
    - Ensure Hadoop services are running or can be started using start-all.sh and hadoop namenode -format
    - Ensure no previous versions of spark instance are running, if running kill them using the following commands:
    
        $ps -ef | grep spark-shell
        $kill -9 "Spark Shell Process ID output from previous command"
       
    - Avro files are in the local Hadoop file system under the following location using following commans:
    
    Location on local file system:
    
    /vagrant/data/categories/categories.avro
    /vagrant/data/products/products.avro
    
    Commands to create a directory in Hadoop file system and copy
    
        hadoop fs -mkdir -p categories
        hadoop fs -mkdir -p products
        hadoop fs -copyFromLocal /vagrant/home/categories/categories.avro /user/vagrant/categories/
        hadoop fs -copyFromLocal /vagrant/home/products/products.avro /user/vagrant/products/
        
    - Start Spark using the following command from spark directory:
        
        $bin/spark-shell --packages com.databricks:spark-avro_2.11:4.0.0
       
    - Imported packages using following command lines in Spark-shell:
    
        //import packages
        import com.databricks.spark.avro._
        import org.apache.spark.sql.SparkSession
        import org.apache.avro
        import org.apache.spark.sql.functions.{row_number, max, broadcast}
        import org.apache.spark.sql.expressions.Window
        
        //Initiate spark session if not done by default
        val spark = SparkSession.builder().master("local").getOrCreate()
       
# Loading data in Spark-shell and displaying first 20 rows of each data set

    - Use the following commands to read avro files in spark-shell:
    
        //Reading avro files from Hadoop file system
        val categories  = spark.read.avro("hdfs://localhost/user/vagrant/categories/categories.avro")
        val products  = spark.read.avro("hdfs://localhost/user/vagrant/products/products.avro")
    
    - Use the following commands to display first 20 rows of each entity
    
        //Displaying 20 records
        categories.show(20)
        products.show(20)

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-13/Results/Result.png)

# Converting Dataframe to RDD

    - Use the following command to convert a dataframe object created first to a RDD object
    
        > val cat_rdd = categories.as[(String,String)].rdd
        > val prod_rdd = products.as[(String,String,String,String,String)].rdd
        
# Filtering product value less than 100 and saving as 'Result_0.txt'
        
    - Filtering out product value less than 100 using the following command
    
        > val result_0 = prod_rdd.filter(_._4.toString != "").filter(_._4.toFloat < 100.00)
        
    - Saving the result_0 (from previous step in a text file)
    
        > result_0.saveAsTextFile("Results_0")
        
    - File from the previous step would be saved in the local directory
    - Use the following commands to convert the resultant in the text file:
    
        $ cat part-00000 > Result_0.txt
    

# Top 10 products prices in each category and saving as 'Result_1.txt'

    - Casting category_id as a Float
    
        >   val cat = categories.withColumn("category_id", 'category_id.cast("Float"))
        >   val prod = products.withColumn("_4", '_4.cast("Float"))
        
    - Joining Categories and Products tables using the category_id as key
    
        > val DFJoin = cat.join(prod, cat("category_id") === products("_2"))

    - Sequencing categories in descending order
    
        > val DFordered = Window.partitionBy($"category_id").orderBy($"_4".desc)
    
    - Joining tables to give output of top 10 products prices in each category
    
        > val top10 = DFJoin.withColumn("row_num",row_number.over(DFordered)).where($"row_num" <= 10).drop("row_num").filter($"_4" < 100)

    - Selecting columns: Category Name, Product Name and Product Price
    
        > val top10DS = top10.select($"category_name",$"_3",$"_4")

    - Renaming columns
    
        > val result_1 = top10DS.withColumnRenamed("category_name","Category Name").withColumnRenamed("_3","Product Name").withColumnRenamed("_4","Product Price")
        
    - Formatting result_1 with tab delimiter
    
        > val result_1_Txt = result_1.map(x => x(0) + "\t" + x(1) + "\t" + x(2))
        
    - Saving result_1_Txt as Text
    
        > result_1_Txt.rdd.saveAsTextFile("Result_1")
        
    - Converting output of Spark in text file
    
        $ cat part* > Result_1.txt
        
# Highest and lowest product price in each category

    - Casting category_id as a Float
        
        >   val cat = categories.withColumn("category_id", 'category_id.cast("Float"))
        >   val prod = products.withColumn("_4", '_4.cast("Float"))
        
    - Joining Categories and Products tables using the category_id as key
        
        > val DFJoin = cat.join(prod, cat("category_id") === products("_2"))
        
    - Sequencing categories in descending order
        
        > val DForderedDesc = Window.partitionBy($"category_id").orderBy($"_4".desc)
    
    - Sequencing categories in descending order
    
        > val DForderedAsc = Window.partitionBy($"category_id").orderBy($"_4".asc)
    
    - Joining tables to select row with Hightest price
    
        > val DFmax = DFJoin.withColumn("row_num",row_number.over(DFordered)).where($"row_num" === 1).drop("row_num")
        
    - Joining tables to select row with Lowest price

        > val DFmin = DFJoin.withColumn("row_num",row_number.over(DForderedAsc)).where($"row_num" === 1).drop("row_num")
        
    - Selecting columns: Category Name, Highest Product Name and Highest Product Price
    
        > val DFmaxSel = DFmax.select($"category_name",$"_3",$"_4")
        > val DFminSel = DFmin.select($"category_name",$"_3",$"_4")
        
    - Renaming columns
    
        > val DFmaxName = DFmaxSel.withColumnRenamed("category_name","Category Name").withColumnRenamed("_3","Highest Product Name").withColumnRenamed("_4","Highest Product Price")
        > val DFminName = DFminSel.withColumnRenamed("category_name","Category Name").withColumnRenamed("_3","Lowest Product Name").withColumnRenamed("_4","Lowest Product Price")
        
    - Joining Maximum and Minimum
    
        > val result_2 = DFmaxName.join(DFminName, "Category_Name")
        
    - Converting file as Avro
    
        > result_2.write.avro("Result_2")
        
    - Converting output of Spark in Avro
    
        $ cat part* > Result_2.avro
