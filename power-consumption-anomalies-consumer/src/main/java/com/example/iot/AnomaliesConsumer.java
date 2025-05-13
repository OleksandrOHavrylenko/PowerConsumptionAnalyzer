package com.example.iot;

import com.example.iot.model.Anomaly;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Oleksandr Havrylenko
 **/
@Component
public class AnomaliesConsumer {
    private static final Logger logger = LoggerFactory.getLogger(AnomaliesConsumer.class);

    @KafkaListener(topics = "${input.topics}")
    public void sinkToConsole(Anomaly anomaly) {
        logger.info("Anomaly Received: {}", anomaly);
    }
}
