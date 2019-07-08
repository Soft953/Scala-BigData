package spark

import org.apache.spark._
import org.apache.spark.SparkContext._
// Implicits that add functions to the SparkContext & RDDs.
import com.datastax.spark.connector._
import org.apache.spark.rdd.RDD

object Main extends App {

  @SerialVersionUID(1L)
  sealed trait CarModel extends Serializable

  case class CarData (
    val id : String,
    val location : String,
    val speed : Float,
    val acceleration : Float,
    val fuel : Float,
    val enginetemp : Float,
    val nextstep : String) extends CarModel

  object CarData {
    def apply(array: Array[String]): CarData = {
      CarData(
        id = array(0),
        location = array(1),
        speed = array(2).toFloat,
        acceleration = array(3).toFloat,
        fuel = array(4).toFloat,
        enginetemp = array(5).toFloat,
        nextstep = array(6))
    }
  }

  def loadData() : (SparkContext, RDD[CarData]) = {
    val sparkMaster = "local[*]"
    val cassandraHost = "localhost"
    val conf = new SparkConf(true)
      .set("spark.cassandra.connection.host", cassandraHost)
      val sc = new SparkContext(sparkMaster, "BasicQueryCassandra", conf)
      sc.setLogLevel("ERROR")
      // entire table as an RDD
      // assumes your table test was created as CREATE TABLE test.kv(key text PRIMARY KEY, value int);
      val data = sc.cassandraTable[CarData]("isd_car_data", "raw_car_data")
      (sc, data)
  }

  def dataToString(data : RDD[CarData]) = {
    data.map(x => x.toString()).fold("")((s1, s2) => s1 + "\n" + s2)
  }

  def slowestCar(data : RDD[CarData]) = {
    data.sortBy(_.speed).take(5)
  }

  def slowestCarWithEngineAlert(data : RDD[CarData]) = {
    slowestCar(data).filter(x => x.enginetemp > 99.0)
  }
  
  def carWithSpeedAlert(data : RDD[CarData]) = {
    data.filter(x => x.speed > 130.0)
  }

  val tmp = loadData()
  val sc = tmp._1
  val data = tmp._2
  System.out.println("Full DataBase : ")
  System.out.println(dataToString(data))

  System.out.println("Slowest Car : ")
  slowestCar(data).foreach(x => System.out.println(x))

  System.out.println("Slowest Car with engine alert: ")
  slowestCarWithEngineAlert(data).foreach(x => System.out.println(x))
  
  System.out.println("Car with speed alert: ")
  carWithSpeedAlert(data).foreach(x => System.out.println(x))

  System.out.println("Done")
  sc.stop
}
