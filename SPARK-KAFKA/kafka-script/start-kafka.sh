#!/bin/sh

KAFKA_SRC="kafka_2.12-2.2.0"
PORT=9092
REPLICATION=1
PARTITIONS=1
TOPIC=test

echo -e "Clear Kafka"

./$KAFKA_SRC/bin/kafka-topics.sh --zookeeper localhost:$PORT --delete --topic $TOPIC
./$KAFKA_SRC/bin/zookeeper-server-stop.sh
./$KAFKA_SRC/bin/kafka-server-stop.sh


echo -e "Start zookeeper server"

./$KAFKA_SRC/bin/zookeeper-server-start.sh $KAFKA_SRC/config/zookeeper.properties &

echo -e "Wait until zookeper start"

sleep 5

echo -e "Start kafka server"

./$KAFKA_SRC/bin/kafka-server-start.sh $KAFKA_SRC/config/server.properties &

echo -e "Wait until kafka start"

sleep 5

echo -e "Create kafka topic"

./$KAFKA_SRC/bin/kafka-topics.sh --create --bootstrap-server localhost:$PORT --replication-factor $REPLICATION --partitions $PARTITIONS --topic $TOPIC
