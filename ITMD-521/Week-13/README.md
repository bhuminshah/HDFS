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

    - Joining Categories and Products tables using the category_id as key
    
        > val DFJoin = categories.join(products, categories("category_id") === products("_2"))

    - Sequencing categories in ascending order
    
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
        
    
# Output Step 1

    - After sucessfull completion we would have a database created with name as 'hadoopguide' and a table created with name 'widgets'

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/1.png)
        
# Step 2

    - Inserting 5000 random records in the table
    - Issue the following command to make the script executable and then run the script
    
    $ chmod 777 step-2.sh
    $ ./step-2.sh
    
# Output Step 2

    - After sucessfull completion we would have 5000 random records (from textbook) inserted in table widgets

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/2.png)


# Step 3

    - Sqoop import on entire 5000 records
    - Issue the following command to make the script executable and then run the script
    
    $ chmod 777 step-3.sh
    $ hadoop fs -rm -r /user/vagrant/widgets
    $ ./step-3.sh

# Output Step 3

    - After sucessfull completion we would have 5000 random records imported in SQOOP
    - Also can be infered from part-m-00000-3 file attached
    
![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/3.png)

# Step 4

    - Sqoop import on record id >=1000 and <=5000
    - Issue the following command to make the script executable and then run the script

    $ chmod 777 step-4.sh
    $ hadoop fs -rm -r /user/vagrant/widgets
    $ ./step-4.sh

# Output Step 4

    - After sucessfull completion we would have records from id = 1000 to 3000 imported in SQOOP
    - Also can be infered from part-m-00000-4 file attached

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/4.png)

# Step 5: Working with Imported data

    - Running a hadoop job on imported data through SQOOP
    - Issue the following command to make the script executable and then run the script

# Assumtions Step5:

    - Script and java files must be in the same folder while executing

##  Commands
    $ chmod 777 step-5.sh
    $ ./step-5.sh


# Output Step 5

    - This script would do the following:
    - Rename class name to Widget
    - Complie Java files
    - Create a jar file using the compiled java and class files
    - Remove any directory in hadoop syste in the following path: /user/vagrant/maxwidget
    - Run job on the data imported from MySQL
    - Display result of part-r-00000
    - After sucessfull completion we would have records our complied jar run on the datanode and result would be prodcued i.e. Max Widget id
    - Also can be infered from part-r-00000-5 file attached

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/5.png)

# Step 6: Average Price of each Widget

    - Running a hadoop job on imported data through SQOOP
    - Issue the following command to make the script executable and then run the script

# Assumtions Step 6:

    - Script and java files must be in the same folder while executing

##  Commands
    $ chmod 777 step-6.sh
    $ ./step-6.sh


# Output Step 6

    - This script would do the following:
    - Rename class name to Widget
    - Complie Java files
    - Create a jar file using the compiled java and class files
    - Remove any directory in hadoop syste in the following path: /user/vagrant/avgprice
    - Run job on the data imported from MySQL
    - Display result of part-r-00000
    - After sucessfull completion we would have records our complied jar run on the datanode and result would be prodcued i.e. Average Price
    - Also can be infered from part-r-00000-6 file attached

![Result image](https://github.com/illinoistech-itm/bshah40/blob/master/ITMD-521/Week-11/6.png)
