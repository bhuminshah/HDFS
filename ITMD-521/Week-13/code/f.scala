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
    


