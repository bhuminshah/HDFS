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
    
    //Convert DataFrame to RDD
    val cat_rdd = categories.as[(String,String)].rdd
    val prod_rdd = products.as[(String,String,String,String,String)].rdd
    
    //Filtering data to produce Price less than 100
    val result_0 = prod_rdd.filter(_._4.toString != "").filter(_._4.toFloat < 100.00)
    
    //Saving the result as Text
    result_0.saveAsTextFile("Result_0")
