package com.example.iot.detector;

import com.example.iot.model.Anomaly;
import com.example.iot.model.PowerConsItem;
import org.apache.kafka.streams.kstream.Windowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Oleksandr Havrylenko
 **/
@Service("anomalyDetector")
public class PowerConsumerAnomalyDetector implements AnomalyDetector{
    private static final Logger logger = LoggerFactory.getLogger(PowerConsumerAnomalyDetector.class);
    public static final double SIGMA_COEFFICIENT = Double.parseDouble(
            System.getenv().getOrDefault("SIGMA_COEFFICIENT", "2.7"));

    @Override
    public List<Anomaly> detect(Windowed<String> localDateWindowed, ArrayList<PowerConsItem> arrayList) {
        final List<Anomaly> room1 = getRoomAnomalies(arrayList, "Room-1", PowerConsItem::subMetering1W);
        final List<Anomaly> room2 = getRoomAnomalies(arrayList, "Room-2", PowerConsItem::subMetering2W);
        final List<Anomaly> room3 = getRoomAnomalies(arrayList, "Room-3", PowerConsItem::subMetering3W);

        return Stream.of(room1, room2, room3).flatMap(Collection::stream).toList();
    }

    private static List<Anomaly> getRoomAnomalies(final ArrayList<PowerConsItem> arrayList,
                                                  final String roomName,
                                                  final ToDoubleFunction<PowerConsItem> roomMapper) {
        final double mu = arrayList.stream().mapToDouble(roomMapper).average().getAsDouble();
        final double sum = arrayList.stream().mapToDouble(roomMapper).map(power -> toSquare(power, mu)).sum();
        final double sigma = Math.sqrt(sum / arrayList.size());
        final double threshold = mu + (SIGMA_COEFFICIENT * sigma);
        return arrayList.stream().filter(item -> roomMapper.applyAsDouble(item) > threshold)
                .map(item -> new Anomaly(LocalDateTime.of(item.date(), item.time()), roomName, roomMapper.applyAsDouble(item)))
                .collect(Collectors.toList());
    }

    private static double toSquare(final double value, final double mu) {
        return (value - mu) * (value - mu);
    }
}
