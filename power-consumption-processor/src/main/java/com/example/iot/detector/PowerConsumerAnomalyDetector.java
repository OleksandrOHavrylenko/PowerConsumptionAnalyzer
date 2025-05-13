package com.example.iot.detector;

import com.example.iot.model.Anomaly;
import com.example.iot.model.PowerConsItem;
import org.apache.kafka.streams.kstream.Windowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Oleksandr Havrylenko
 **/
@Service("anomalyDetector")
public class PowerConsumerAnomalyDetector implements AnomalyDetector{
    private static final Logger logger = LoggerFactory.getLogger(PowerConsumerAnomalyDetector.class);

    @Override
    public Optional<Anomaly> detect(Windowed<String> localDateWindowed, ArrayList<PowerConsItem> arrayList) {
        Optional<PowerConsItem> max = arrayList.stream()
                .max(Comparator.comparing(PowerConsItem::subMetering1W));

        return max.map(value -> new Anomaly(LocalDateTime.of(value.date(), value.time()), "room1", value.subMetering1W()));
    }
}
