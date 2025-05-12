package com.example.iot.serdes;

import com.example.iot.model.Anomaly;
import com.example.iot.serialization.AnomalyDeserializer;
import com.example.iot.serialization.AnomalySerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

/**
 * @author Oleksandr Havrylenko
 **/
public final class CustomSerdes {
    private CustomSerdes() {}
    public static Serde<Anomaly> Anomaly() {
        return Serdes.serdeFrom(new AnomalySerializer(), new AnomalyDeserializer());
    }
}
