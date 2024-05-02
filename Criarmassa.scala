import org.apache.spark.sql.{SparkSession, Row}
import org.apache.spark.sql.types._

object AddressDataGenerator {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("AddressDataGenerator")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Defina o esquema para o DataFrame
    val schema = StructType(Seq(
      StructField("street", StringType, nullable = false),
      StructField("city", StringType, nullable = false),
      StructField("state", StringType, nullable = false),
      StructField("zip", IntegerType, nullable = false)
    ))

    // Crie o DataFrame vazio com o esquema definido
    val rdd = spark.sparkContext.emptyRDD[Row]
    var df = spark.createDataFrame(rdd, schema)

    // Popule o DataFrame com dados aleatórios
    df = df.withColumn("street", concat(lit("Street "), rand().cast("int")))
          .withColumn("city", concat(lit("City "), rand().cast("int")))
          .withColumn("state", concat(lit("State "), rand().cast("int")))
          .withColumn("zip", (rand() * 1000000).cast("int"))

    // Exporte os dados gerados para o formato desejado
    df.write.mode("overwrite").csv("/caminho/para/salvar/os/dados")
    
    spark.stop()
  }
}
 org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object AddressDataGenerator {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("AddressDataGenerator")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Defina o esquema para o DataFrame
    val schema = List("street", "city", "state", "zip")

    // Crie o DataFrame vazio com o esquema definido
    var df = spark.createDataFrame(spark.sparkContext.emptyRDD[Address].toDS)

    // Popule o DataFrame com dados aleatórios
    df = df.withColumn("street", concat(lit("Street "), rand().cast("int")))
          .withColumn("city", concat(lit("City "), rand().cast("int")))
          .withColumn("state", concat(lit("State "), rand().cast("int")))
          .withColumn("zip", (rand() * 1000000).cast("int"))

    // Exporte os dados gerados para o formato desejado
    df.write.mode("overwrite").csv("/caminho/para/salvar/os/dados")
    
    spark.stop()
  }
}

case class Address(street: String, city: String, state: String, zip: Int)

