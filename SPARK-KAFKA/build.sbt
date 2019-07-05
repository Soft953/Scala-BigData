name := "Spark Project"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.2"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "1.6.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.6.2"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka" % "1.6.2"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "1.6.0"
//libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6"
//libraryDependencies +=  "com.typesafe.play" %% "play-json" % "2.4.0"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"
