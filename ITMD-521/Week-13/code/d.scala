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
