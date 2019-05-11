# Scala-BigData

Pour lancer le serveur, exécuter la commande suivante à l'intérieur du dossier 'PLAY-SBT':
    # sbt run

Pour lancer le client, exécuter la commande suivante à l'intérieur du dossier 'CLIENT/Scala Project':
    # sbt run

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
