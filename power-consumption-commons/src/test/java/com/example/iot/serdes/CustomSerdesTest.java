package com.example.iot.serdes;

import com.example.iot.model.Anomaly;
import org.apache.kafka.common.serialization.Serde;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Oleksandr Havrylenko
 **/
class CustomSerdesTest {
    Serde<Anomaly> anomalySerde = CustomSerdes.Anomaly();

    @Test
    void serializeDeserializeTest() {
        final Anomaly expected = new Anomaly(LocalDateTime.of(2025, 1, 5, 10, 25), "room1", 10.0);

        byte[] serializedData = anomalySerde.serializer().serialize("key", expected);
        assertNotNull(serializedData);

        final Anomaly actual = anomalySerde.deserializer().deserialize("key", serializedData);
        assertNotNull(actual);

        assertEquals(expected, actual);
    }

}