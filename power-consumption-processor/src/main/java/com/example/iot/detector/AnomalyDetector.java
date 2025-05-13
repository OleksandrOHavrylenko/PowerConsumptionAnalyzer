package com.example.iot.detector;

import com.example.iot.model.Anomaly;
import com.example.iot.model.PowerConsItem;
import org.apache.kafka.streams.kstream.Windowed;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Oleksandr Havrylenko
 **/
public interface AnomalyDetector {
    List<Anomaly> detect(Windowed<String> localDateWindowed, ArrayList<PowerConsItem> arrayList);
}
