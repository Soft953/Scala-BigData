package spark

import java.util.{Date, Properties}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.util.Random

object CarProducer extends App {
  val events = 10000//args(0).toInt
  val topic = "raw_cardata"//args(1)
  val brokers = "localhost:9092"//args(2)
  val rnd = new Random()
  val props = new Properties()
  props.put("bootstrap.servers", brokers)
  props.put("client.id", "ScalaProducerExample")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  val producer = new KafkaProducer[String, String](props)
  val t = System.currentTimeMillis()
/*  for (nEvents <- Range(0, events)) {
    val runtime = new Date().getTime()
    val ip = "192.168.2." + rnd.nextInt(255)
    val msg = runtime + "," + nEvents + ",www.example.com," + ip
*/
    //async
    //producer.send(data, (m,e) => {})
    //sync
    //
    val carcsv = "2789;4889;67.8;2.3;99.6;100.0;A1-Paris"
    val data = new ProducerRecord[String, String](topic, carcsv)

    producer.send(data)
  //}

  System.out.println("sent per second: " + events * 1000 / (System.currentTimeMillis() - t))
  producer.close()
}
