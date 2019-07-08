# Scala-BigData

S'assurer d'avoir une base de données Cassandra en background.

    CLIENT
      |
      V
    BACKEND
      |
      V
    KAFKA        Spark RDD Data Analyse
      |                   ^
      V                   |
    Consumer --> Cassandra Database

Pour lancer le serveur, exécuter la commande suivante à l'intérieur du dossier 'PLAY-SBT':
    # make clean
    # make build
    # make start

Pour lancer le client, exécuter la commande suivante à l'intérieur du dossier 'CLIENT/Scala Project':
    # sbt run
    
Pour lancer le CarConsumer Kafka, exécuter la commande suivante à l'intérieur du dossier 'SPARK-KAFKA/':
    # sbt run
    # Choose CarConsumer Main
    
Pour lancer Spark et avoir des analyses sur la base de données, exécuter la commande suivante à l'intérieur du dossier 'SPARK-KAFKA/':
    # sbt run
    # Choose Main

Les différentes routes disponible sont:
    GET localhost:9000/api/ping -> permet de tester si le serveur est lancé

    POST localhost:9000/info -> permet à une voiture de savoir quelle est la prochaine action à effectuer.
    reçoit une JSON de la forme:
        {
            "id": int,
            "location": string,
            "speed": float,
            "acceleration": float,
            "fuel": float,
            "engineTemp": float,
            "nextStep": string
        }

    et renvoie un JSON de la forme:
        {
            "code": int,             # 0: pas de warning, 2: warning #
            "message": string,
            "content": string
        }
