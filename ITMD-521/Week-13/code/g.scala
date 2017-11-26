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
    
    val cat = categories.withColumn("category_id", 'category_id.cast("Float"))
    val prod = products.withColumn("_4", '_4.cast("Float"))
    
    //Joining Categories and Products tables using the category_id as key
    val DFJoin = cat.join(prod, cat("category_id") === products("_2"))
    
    val DForderedDesc = Window.partitionBy($"category_id").orderBy($"_4".desc)
    
    //Sequencing categories in descending order
    val DForderedAsc = Window.partitionBy($"category_id").orderBy($"_4".asc)
    
    //Joining tables to select row with Hightest price
    val DFmax = DFJoin.withColumn("row_num",row_number.over(DForderedDesc)).where($"row_num" === 1).drop("row_num")
    
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
