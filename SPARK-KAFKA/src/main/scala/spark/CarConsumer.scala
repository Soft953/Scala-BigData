package spark

import kafka.serializer.StringDecoder
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils
/*
class CarData (val id : String, val location : String, val speed : Float,
               val acceleration : Float, val fuel : Float, val engineTemp : Float,
               val nextStep : String) {

  val _id = id
  val _location = location
  val _speed = speed
  val _acceleration = acceleration
  val _fuel = fuel
  val _engineTemp = engineTemp
  val _nextStep = nextStep

  def toJSON = "{\"id\": " + _id + ", \"location\": \"" + _location.toString + "\", \"speed\":" + _speed.toString + "" +
    ", "+ "\"acceleration\": " +  _acceleration.toString + ", \"fuel\":" + _fuel + ", \"engineTemp\": " + _engineTemp.toString + ", \"nextStep\": \"" + _nextStep + "\"}";
  
  override def toString = s"${_id};${_location};${_speed.toString};" +
    s"${_acceleration.toString};${_fuel.toString};${_engineTemp.toString};${_nextStep}"
}
*/
object CarConsumer {

  @SerialVersionUID(1L)
  sealed trait CarModel extends Serializable
  
  case class CarData (
      val id : String,
      val location : String,
      val speed : Float,
      val acceleration : Float,
      val fuel : Float,
      val engineTemp : Float,
      val nextStep : String) extends CarModel

  object CarData {
    def apply(array: Array[String]): CarData = {
      CarData(
        id = array(0),
        location = array(1),
        speed = array(2).toFloat,
        acceleration = array(3).toFloat,
        fuel = array(4).toFloat,
        engineTemp = array(5).toFloat,
        nextStep = array(6))
    }
  }  
  /*case class RawWeatherData(
                             wsid: String,
                             year: Int,
                             month: Int,
                             day: Int,
                             hour: Int,
                             temperature: Double,
                             dewpoint: Double,
                             pressure: Double,
                             windDirection: Int,
                             windSpeed: Double,
                             skyCondition: Int,
                             skyConditionText: String,
                             oneHourPrecip: Double,
                             sixHourPrecip: Double) extends WeatherModel

  object RawWeatherData {
    def apply(array: Array[String]): RawWeatherData = {
      RawWeatherData(
        wsid = array(0),
        year = array(1).toInt,
        month = array(2).toInt,
        day = array(3).toInt,
        hour = array(4).toInt,
        temperature = array(5).toDouble,
        dewpoint = array(6).toDouble,
        pressure = array(7).toDouble,
        windDirection = array(8).toInt,
        windSpeed = array(9).toDouble,
        skyCondition = array(10).toInt,
        skyConditionText = array(11),
        oneHourPrecip = array(11).toDouble,
        sixHourPrecip = Option(array(12).toDouble).getOrElse(0))
    }
  }*/
  
 def ingestStream(rawWeatherStream: InputDStream[(String, String)]): DStream[CarData] = {
    val parsedWeatherStream = rawWeatherStream.map(_._2.split(";"))
      .map(CarData(_))
    parsedWeatherStream
  }


  val localLogger = Logger.getLogger("WeatherDataStream")

  def main(args: Array[String]) {

    // update
    // val checkpointDir = "./tmp"

    val sparkConf = new SparkConf().setAppName("Car Data")
    sparkConf.setIfMissing("spark.master", "local[5]")
//    sparkConf.setIfMissing("spark.checkpoint.dir", checkpointDir)
    sparkConf.setIfMissing("spark.cassandra.connection.host", "127.0.0.1")

    val ssc = new StreamingContext(sparkConf, Seconds(2))

    val kafkaTopicRaw = "raw_cardata"
    val kafkaBroker = "127.0.01:9092"

    //val cassandraKeyspace = "isd_weather_data"
    //val cassandraTableRaw = "raw_weather_data"
    //val cassandraTableDailyPrecip = "daily_aggregate_precip"

    //println(s"using cassandraTableDailyPrecip $cassandraTableDailyPrecip")

    val topics: Set[String] = kafkaTopicRaw.split(",").map(_.trim).toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> kafkaBroker)

    localLogger.info(s"connecting to brokers: $kafkaBroker")
    localLogger.info(s"kafkaParams: $kafkaParams")
    localLogger.info(s"topics: $topics")

    val rawWeatherStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    val parsedWeatherStream: DStream[CarData] = ingestStream(rawWeatherStream)

//    persist(cassandraKeyspace, cassandraTableRaw, cassandraTableDailyPrecip, parsedWeatherStream)

    //println(rawWeatherStream)
    parsedWeatherStream.print // for demo purposes only

    //Kick off
    ssc.start()

    ssc.awaitTermination()

    ssc.stop()
  }
}
