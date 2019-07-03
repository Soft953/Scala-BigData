package spark


import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._

object Main extends App {
  //getListOfFiles("ressources/").foreach(x => files(x.getAbsolutePath))

  val pathToFile = "carData.csv"

  // create spark configuration and spark context: the Spark context is the entry point in Spark.
  // It represents the connexion to Spark and it is the place where you can configure the common properties
  // like the app name, the master url, memories allocation...
  val ss = SparkSession.builder()
      .appName("WordcountDF")
      .master("local[*]")
      .getOrCreate()


  def loadData(): DataFrame = {
      ss.read.format("csv").option("header", "true").option("delimiter", ";").load(pathToFile)
  }
  
  println("DATA ->")
  val data = loadData()
  data.show()
  ss.stop()


}
