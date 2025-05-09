package com.example.iot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Oleksandr Havrylenko
 **/
@Component
public class PowerConsumptionProducer {
    private static final Logger logger = LoggerFactory.getLogger(PowerConsumptionProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String outputTopic = System.getenv().getOrDefault("OUTPUT_TOPIC", "input-power");
    private final String dlq = System.getenv().getOrDefault("DEAD_LETTER_QUEUE", "dlq");
    private final String filePath = System.getenv().getOrDefault("INPUT_FILE_PATH", "input/power_consumption_data.txt");

    public void produce() {
        try {
            List<String> linesToProduce = Files.readAllLines(Paths.get(filePath));
            linesToProduce.stream()
                    .skip(1)
                    .forEach(this::sendMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(final String line) {
        String[] splittedData = line.split(";");
        if (splittedData.length != 9) {
            logger.error("Error in  power consumption data: size={}, line: {}, ", splittedData.length, splittedData);
            kafkaTemplate.send(this.dlq, "producer" , line);
        } else {
            kafkaTemplate.send(this.outputTopic, line);
        }
    }
}
