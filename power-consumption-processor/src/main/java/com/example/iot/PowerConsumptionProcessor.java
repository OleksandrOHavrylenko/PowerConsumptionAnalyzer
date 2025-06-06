package com.example.iot;

import com.example.iot.detector.AnomalyDetector;
import com.example.iot.extractor.PowerConsTimestampExtractor;
import com.example.iot.mapper.ModelMapper;
import com.example.iot.model.PowerConsItem;
import com.example.iot.serdes.CustomSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Optional;

/**
 * @author Oleksandr Havrylenko
 **/
@Component
public class PowerConsumptionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PowerConsumptionProcessor.class);
    public static final String INPUT_TOPIC = System.getenv()
            .getOrDefault("INPUT_TOPIC", "input-power");
    public static final String OUTPUT_TOPIC = System.getenv()
            .getOrDefault("OUTPUT_TOPIC", "power-anomalies");
    public static final String ANOMALIES_DETECTED_STORE = "anomalies-detected-store";

    @Autowired
    private AnomalyDetector anomalyDetector;
    @Autowired
    private ModelMapper<PowerConsItem> modelMapper;

    @Autowired
    void process(final StreamsBuilder streamsBuilder) {
        KStream<String, String> inputPowerStream = streamsBuilder
                .stream(INPUT_TOPIC,
                        Consumed.with(Serdes.String(), Serdes.String())
                                .withTimestampExtractor(new PowerConsTimestampExtractor())
                );

        final Duration windowSize = Duration.ofHours(3);
        final TimeWindows tumblingWindow = TimeWindows.ofSizeWithNoGrace(windowSize);

        inputPowerStream
                .mapValues(this::parseLine)
                .filter((key, value) -> value.isPresent())
                .mapValues(Optional::get)
                .peek((key, value) -> logger.info("key: " + key + " value: " + value))
                .groupBy((key, value) -> "", Grouped.with(Serdes.String(), CustomSerdes.PowerConsItem()))
                .windowedBy(tumblingWindow)
                .aggregate(ArrayList::new, this::aggregate, Materialized.<String, ArrayList<PowerConsItem>, WindowStore<Bytes, byte[]>>as(ANOMALIES_DETECTED_STORE)
                        .withKeySerde(Serdes.String())
                        .withValueSerde(CustomSerdes.ArrayListOfPowerConsItems()))
                .toStream()
                .mapValues(anomalyDetector::detect)
                .flatMapValues(v -> v)
                .map((key,value) -> KeyValue.pair(value.room(), value))
                .peek((key, value) -> logger.debug("Output => key: " + key + " value: " + value))
                .to(OUTPUT_TOPIC, Produced.with(Serdes.String(), CustomSerdes.Anomaly()));
    }

    private ArrayList<PowerConsItem> aggregate(final String date, final PowerConsItem powerConsItem, final ArrayList<PowerConsItem> arrayList) {
        arrayList.add(powerConsItem);
        return arrayList;
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
