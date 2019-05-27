.PHONY: build start clean

build:
	bash -c "sbt dist"
	bash -c "set -x && unzip -d svc target/universal/*-1.0-SNAPSHOT.zip && mv svc/*/* svc/ && rm svc/bin/*.bat && mv svc/bin/* svc/bin/start"

start:
	bash -c "sudo docker build -t scala-api ."
	bash -c "sudo docker run -it -p 9000:9000 -p 9443:9443 --rm scala-api"

clean:
	bash -c "rm -rf svc"
	bash -c "sudo docker image rm scala-api"
