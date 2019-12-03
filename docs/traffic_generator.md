# Generating traffic

In order to explore the observability of our system, we need realistic data flowing through our system.

## Default traffic generator

At startup of the application the [traffic generator](../stack/infrastructure/traffic-gen/Dockerfile) is started. This will upload a number of free online images in bulk and then progressively transform them at a steady rate.

## Restarting the generator

You may have a need to restart the traffic generator to reupload a bulk of images. To do this you must first stop and remove the current traffic generator container.

> Prerequisite: you will need your command line to be within the context of your running application. To check, run `docker ps | grep traffic-gen` and you should see the running application.

Stop and remove the current traffic generator with:
```
docker rm $(docker stop $(docker ps -a -q --filter ancestor=dima_traffic-gen --format="{{.ID}}"))
```

Restart the traffic generator by running the same level you originally built the application with. E.g.:
```
./start-stack-in-level.sh 5
```


#### Run an additional or different traffic generator

> Prerequisite: you will need to have go and vegeta installed on your machine before proceeding

1. To upload traffic via a vegata style attack:
```
go run main.go -d ~/work/flickrscrape/result -u http://<public_ip_of_docker_machine>:8080/api/images | \
  vegeta attack -rate=10/m -lazy -format=json -duration=30s | \
  tee results.bin | \
  vegeta report
```