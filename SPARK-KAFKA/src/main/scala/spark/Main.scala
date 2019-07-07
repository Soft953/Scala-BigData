package spark

import org.apache.spark._
import org.apache.spark.SparkContext._
// Implicits that add functions to the SparkContext & RDDs.
import com.datastax.spark.connector._

object Main extends App {

  val sparkMaster = "local[*]"
  val cassandraHost = "localhost"
  val conf = new SparkConf(true)
    .set("spark.cassandra.connection.host", cassandraHost)
    val sc = new SparkContext(sparkMaster, "BasicQueryCassandra", conf)
    // entire table as an RDD
    // assumes your table test was created as CREATE TABLE test.kv(key text PRIMARY KEY, value int);
    val data = sc.cassandraTable("isd_car_data", "raw_car_data")

    //val df = spark.read.format("org.apache.spark.sql.cassandra").options(Map( "table" -> "raw_car_data", "keyspace" -> "ids_car_data")).load() // This Dataset will use a spark.cassandra.input.size of 128
    data.foreach(car => System.out.println(car))
}
