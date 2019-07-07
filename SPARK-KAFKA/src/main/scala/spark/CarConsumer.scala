package spark

import kafka.serializer.StringDecoder
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.KafkaUtils

object CarConsumer {

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

  def ingestStream(rawWeatherStream: InputDStream[(String, String)]): DStream[CarData] = {
    val parsedWeatherStream = rawWeatherStream.map(_._2.split(";"))
      .map(CarData(_))
    parsedWeatherStream
  }

  def persist(CassandraKeyspace: String, CassandraTableRaw: String, 
    parsedWeatherStream: DStream[CarData]): Unit = {

    import com.datastax.spark.connector.streaming._

    /** Saves the raw data to Cassandra - raw table. */
    parsedWeatherStream.saveToCassandra(CassandraKeyspace, CassandraTableRaw)
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

    val cassandraKeyspace = "isd_car_data"
    val cassandraTableRaw = "raw_car_data"

    //println(s"using cassandraTableDailyPrecip $cassandraTableDailyPrecip")

    val topics: Set[String] = kafkaTopicRaw.split(",").map(_.trim).toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> kafkaBroker)

    localLogger.info(s"connecting to brokers: $kafkaBroker")
    localLogger.info(s"kafkaParams: $kafkaParams")
    localLogger.info(s"topics: $topics")

    val rawWeatherStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
    val parsedWeatherStream: DStream[CarData] = ingestStream(rawWeatherStream)
    
    persist(cassandraKeyspace, cassandraTableRaw, parsedWeatherStream)

    //println(rawWeatherStream)
    parsedWeatherStream.print // for demo purposes only

    //Kick off
    ssc.start()

    ssc.awaitTermination()

    ssc.stop()
  }
}
