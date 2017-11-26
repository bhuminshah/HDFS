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
        
    - script and scala files should be in the same folder in spark directory
       
# Loading data in Spark-shell and displaying first 20 rows of each data set

    - Use the following commands to read avro files in spark-shell:
    
       chmod 777 *.sh
       ./part-d.sh

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-13/Results/Result.png)

        
# Filtering product price less than 100 and saving as 'Result_0.txt'

    - Use the following commands to filter data with product price less than 100
    
        chmod 777 *.sh
        ./part-e.sh
        
    - Quit the spark shell
    - Change directory to Result_0 and paste the below command:
    
        cat part-00000 > Result_0.txt
    

# Top 10 products prices in each category and saving as 'Result_1.txt'

    - Use the following commands to find the top 10 products prices in each category
    
        chmod 777 *.sh
        ./part-f.sh
        
    - Quit the spark shell
    - Change directory to Result_1 and paste the below command:
    
        cat part* > Result_1.txt
        
# Highest and lowest product price in each category

    - Use the following commands to find the highest and lowest product price in each category
    
        chmod 777 *.sh
        ./part-g.sh
        
    - Quit the spark shell
    - Change directory to Result_2 and paste the below command:
    
        $ cat part* > Result_2.avro
        
        
# Complete code

    //import packages
    import com.databricks.spark.avro._
    import org.apache.spark.sql.SparkSession
    import org.apache.avro
    import org.apache.spark.sql.functions.{row_number, max, broadcast}
    import org.apache.spark.sql.expressions.Window
    
    //Initiating a spark session
    val spark = SparkSession.builder().master("local").getOrCreate()
    
    //Reading avro files from Hadoop file system
    val categories  = spark.read.avro("hdfs://localhost/user/vagrant/categories/categories.avro")
    val products  = spark.read.avro("hdfs://localhost/user/vagrant/products/products.avro")
    
    //Display 20 records for each
    categories.show(20)
    products.show(20)
    
    //Convert DataFrame to RDD
    val cat_rdd = categories.as[(String,String)].rdd
    val prod_rdd = products.as[(String,String,String,String,String)].rdd
    
    //Filtering data to produce Price less than 100
    val result_0 = prod_rdd.filter(_._4.toString != "").filter(_._4.toFloat < 100.00)
    
    //Saving the result as Text
    result_0.saveAsTextFile("Results_0")
    
    //Linux command: Creating a text file from the Spark output
    $ cat part-00000 > Result_0.txt
    
    //Casting category_id to Float
    val cat = categories.withColumn("category_id", 'category_id.cast("Float"))
    val prod = products.withColumn("_4", '_4.cast("Float"))
    
    //Joining Categories and Products tables using the category_id as key
    val DFJoin = cat.join(prod, cat("category_id") === products("_2"))
    
    //Sequencing categories in descending order
    val DFordered = Window.partitionBy($"category_id").orderBy($"_4".desc)
    
    //Joining tables to give output of top 10 products prices in each category
    val top10 = DFJoin.withColumn("row_num",row_number.over(DFordered)).where($"row_num" <= 10).drop("row_num").filter($"_4" < 100)
    
    //Selecting columns: Category Name, Product Name and Product Price
    val top10DS = top10.select($"category_name",$"_3",$"_4")
    
    //Renaming columns
    val result_1 = top10DS.withColumnRenamed("category_name","Category Name").withColumnRenamed("_3","Product Name").withColumnRenamed("_4","Product Price")
    
    //Formatting result_1 with tab delimiter
    val result_1_Txt = result_1.map(x => x(0) + "\t" + x(1) + "\t" + x(2))
    
    //Saving result_1_Txt as Text
    result_1_Txt.rdd.saveAsTextFile("Result_1")
    
    //Linux command: Converting output of Spark in text file
    $cat part* > Result_1.txt
    
    //Sequencing categories in descending order
    val DForderedDesc = Window.partitionBy($"category_id").orderBy($"_4".desc)
    
    //Sequencing categories in descending order
    val DForderedAsc = Window.partitionBy($"category_id").orderBy($"_4".asc)
    
    //Joining tables to select row with Hightest price
    val DFmax = DFJoin.withColumn("row_num",row_number.over(DFordered)).where($"row_num" === 1).drop("row_num")
    
    //Joining tables to select row with Lowest price
    val DFmin = DFJoin.withColumn("row_num",row_number.over(DForderedAsc)).where($"row_num" === 1).drop("row_num")
    
    //Selecting columns: Category Name, Highest Product Name and Highest Product Price
    val DFmaxSel = DFmax.select($"category_name",$"_3",$"_4")
    val DFminSel = DFmin.select($"category_name",$"_3",$"_4")
    
    //Renaming columns
    val DFmaxName = DFmaxSel.withColumnRenamed("category_name","Category_Name").withColumnRenamed("_3","Highest_Product_Name").withColumnRenamed("_4","Highest_Product_Price")
    val DFminName = DFminSel.withColumnRenamed("category_name","Category_Name").withColumnRenamed("_3","Lowest_Product_Name").withColumnRenamed("_4","Lowest_Product_Price")
    
    //Joining Maximum and Minimum
    val result_2 = DFmaxName.join(DFminName, "Category_Name")
    
    //Formatting result_1 with tab delimiter
    val result_2_Avro = result_2.map(x => x(0) + "|" + x(1) + "|" + x(2))
    
    //Converting file as Avro
    result_2_Avro.write.avro("Result_2")
    
    //Linux Command: Converting output of Spark in Avro
    $ cat part* > Result_2.avro
    
    
    
    
