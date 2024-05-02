import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object AddressDataGenerator {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("AddressDataGenerator")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Função para gerar um número aleatório dentro de um intervalo
    def randomInRange(min: Int, max: Int) = {
      min + scala.util.Random.nextInt((max - min) + 1)
    }

    // Função para gerar um endereço aleatório
    def generateAddress(): (String, String, String, String, String) = {
      val streets = Array("Rua A", "Rua B", "Rua C", "Avenida X", "Avenida Y")
      val cities = Array("São Paulo", "Rio de Janeiro", "Belo Horizonte", "Brasília", "Curitiba")
      val states = Array("SP", "RJ", "MG", "DF", "PR")
      val zip = randomInRange(10000, 99999).toString
      val street = streets(randomInRange(0, streets.length - 1))
      val city = cities(randomInRange(0, cities.length - 1))
      val state = states(randomInRange(0, states.length - 1))
      val number = randomInRange(1, 100).toString
      (street, number, city, state, zip)
    }

    // Gerar 1000 endereços aleatórios
    val addressData = (1 to 1000).map(_ => generateAddress()).toSeq.toDS()

    // Criar DataFrame a partir dos dados gerados
    val df = addressData.toDF("street", "number", "city", "state", "zip")

    // Salvar os dados como um arquivo CSV
    df.write.mode("overwrite").csv("/caminho/para/salvar/os/dados")

    spark.stop()
  }
}





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

