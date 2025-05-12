#!/bin/bash
docker exec broker-1 ./opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 --delete --group power-consumption-processor
docker exec broker-1 ./opt/kafka/bin/kafka-consumer-groups.sh --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 --delete --group power-anomalies-consumer
docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 --delete --topic input-power
docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 --delete --topic dlq
docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 --delete --topic power-anomalies
docker exec broker-1 ./opt/kafka/bin/kafka-topics.sh --bootstrap-server broker-1:19092,broker-2:19092,broker-3:19092 --list