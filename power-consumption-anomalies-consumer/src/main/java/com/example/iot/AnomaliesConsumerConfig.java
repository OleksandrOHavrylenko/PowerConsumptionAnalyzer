package com.example.iot;

import com.example.iot.model.Anomaly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author Oleksandr Havrylenko
 **/
@Configuration
public class AnomaliesConsumerConfig {
    private static final Logger logger = LoggerFactory.getLogger(AnomaliesConsumerConfig.class);

    @KafkaListener(topics = "${input.topics}")
    public void sinkToConsole(Anomaly anomaly) {
        logger.info("Anomaly Received: {}", anomaly);
    }
}
