package com.example.iot;

import com.example.iot.mapper.ModelMapper;
import com.example.iot.mapper.PowerConsMapper;
import com.example.iot.model.Anomaly;
import com.example.iot.model.PowerConsItem;
import com.example.iot.serdes.CustomSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

/**
 * @author Oleksandr Havrylenko
 **/
@Component
public class PowerConsumptionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PowerConsumptionProcessor.class);
    public static final String INPUT_TOPIC = System.getenv()
            .getOrDefault("INPUT_TOPIC", "input-power");
    public static final String OUTPUT_TOPIC = "output-anomalies-detected";
    public static final String DOMAIN_COUNTS_STORE = "anomalies-detected-store";

    private ModelMapper<PowerConsItem> modelMapper = new PowerConsMapper();

    @Autowired
    void process(final StreamsBuilder streamsBuilder) {
        KStream<String, String> inputPowerStream = streamsBuilder
                .stream(INPUT_TOPIC, Consumed.with(Serdes.String(), Serdes.String()));

        inputPowerStream
                .mapValues(this::parseLine)
                .filter((key, value) -> value.isPresent())
                .mapValues(Optional::get)
                .mapValues(this::detectAnomalies)
                .filter((key, value) -> value.isPresent())
                .mapValues(Optional::get)
                .peek((key, value) -> logger.debug("key: " + key + " value: " + value))
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), CustomSerdes.Anomaly()));
    }

//    TODO fake method just for streams testing. Change to real AnomalyDetector
    private Optional<Anomaly> detectAnomalies(final String key, final PowerConsItem powerConsItem) {
        int nextInt = new Random().nextInt();
        if(nextInt > 0 && nextInt < 100) {
            return Optional.ofNullable(new Anomaly(LocalDateTime.now(), "room1", 100.0));
        } else {
            return Optional.empty();
        }
    }

    private Optional<PowerConsItem> parseLine(final String line) {
        try {
            return Optional.ofNullable(modelMapper.parseLine(line));
        } catch (RuntimeException e) {
            logger.error("Model mapper could not parse line", e);
            return Optional.empty();
        }
    }
}
