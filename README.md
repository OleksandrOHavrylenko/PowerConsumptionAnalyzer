# How to Run Household Power Consumption Analyzer Project
### 0 Prepare correct power_consumption_data.txt file. 
> Put correct power_consumption_data.txt file to /power-consumption-generator/input directory.
power_consumption_data.txt file format should be like /power-consumption-generator/input/power_consumption-example.txt


### 1 Start Kafka cluster with the command from the root folder of the project.
```Shell
docker-compose -f docker-compose-kafka-kraft.yml up -d
```

### 2 Run init script
Wait for 20 seconds after previous step.
```Shell
./scripts/init.sh
```

### 3 Start docker-compose file to run power-consumption microservices

```Shell
docker-compose -f compose.yml up -d --build && docker-compose rm -f
```

### 4 Check the result on power-anomalies-consumer microservice.

```Shell
docker logs power-anomalies-consumer --follow
```

### 5 Stop power-consumption microservices.

```Shell
docker-compose -f compose.yml down
```

### 6 Clean Kafka consumer groups and topics.
Wait for 30 seconds after previous step.
```Shell
./scripts/clean.sh
```
### 7 Stop Kafka cluster.
```Shell
docker-compose -f docker-compose-kafka-kraft.yml down
```