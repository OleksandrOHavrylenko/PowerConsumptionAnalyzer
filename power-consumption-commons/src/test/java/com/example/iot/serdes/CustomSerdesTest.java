package com.example.iot.serdes;

import com.example.iot.model.Anomaly;
import com.example.iot.model.PowerConsItem;
import org.apache.kafka.common.serialization.Serde;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Oleksandr Havrylenko
 **/
class CustomSerdesTest {
    Serde<Anomaly> anomalySerde = CustomSerdes.Anomaly();
    Serde<PowerConsItem> powerConsItemSerde = CustomSerdes.PowerConsItem();
    Serde<ArrayList<PowerConsItem>> arrayListSerde = CustomSerdes.ArrayListOfPowerConsItems();

    @Test
    void serializeDeserializeTest() {
        final Anomaly expected = new Anomaly(LocalDateTime.of(2025, 1, 5, 10, 25), "room1", 10.0);

        byte[] serializedData = anomalySerde.serializer().serialize("key", expected);
        assertNotNull(serializedData);

        final Anomaly actual = anomalySerde.deserializer().deserialize("key", serializedData);
        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void serializePowerConsItemDeserializeTest() {
        final PowerConsItem expected = new PowerConsItem(
                LocalDate.of(2006, 12, 16),
                LocalTime.of(17, 24, 0),
                4216.0,
                418.0,
                234.84,
                18.4,
                0.0,
                1.0,
                17.0);

        byte[] serializedData = powerConsItemSerde.serializer().serialize("key", expected);
        assertNotNull(serializedData);
        System.out.println(new String(serializedData));

        final PowerConsItem actual = powerConsItemSerde.deserializer().deserialize("key", serializedData);
        assertNotNull(actual);

        assertEquals(expected, actual);
    }

    @Test
    void serializeDeserializeArrayListTest() {
        ArrayList<PowerConsItem> expected = new ArrayList<>();
        final PowerConsItem pci1 = new PowerConsItem(
                LocalDate.of(2006, 12, 16),
                LocalTime.of(17, 24, 0),
                4216.0,
                418.0,
                234.84,
                18.4,
                0.0,
                1.0,
                17.0);

        final PowerConsItem pci2 = new PowerConsItem(
                LocalDate.of(2006, 12, 16),
                LocalTime.of(17, 25, 0),
                4216.0,
                410.0,
                234.84,
                18.4,
                0.0,
                1.5,
                17.0);

        expected.add(pci1);
        expected.add(pci2);

        byte[] serializedData = arrayListSerde.serializer().serialize("key", expected);
        assertNotNull(serializedData);
        System.out.println(new String(serializedData));

        final ArrayList<PowerConsItem> actual = arrayListSerde.deserializer().deserialize("key", serializedData);
        assertNotNull(actual);

        assertEquals(expected, actual);
    }

}