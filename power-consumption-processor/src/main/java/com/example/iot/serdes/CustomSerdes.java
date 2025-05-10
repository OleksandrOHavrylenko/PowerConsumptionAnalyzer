package com.example.iot.serdes;

import com.example.iot.model.Anomaly;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author Oleksandr Havrylenko
 **/
public final class CustomSerdes {
    private CustomSerdes() {}
    public static Serde<Anomaly> Anomaly() {
        JsonSerializer<Anomaly> serializer = new JsonSerializer<>();
        JsonDeserializer<Anomaly> deserializer = new JsonDeserializer<>(Anomaly.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

}
