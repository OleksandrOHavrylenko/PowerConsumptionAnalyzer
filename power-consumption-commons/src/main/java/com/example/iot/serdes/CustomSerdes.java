package com.example.iot.serdes;

import com.example.iot.model.Anomaly;
import com.example.iot.model.PowerConsItem;
import com.example.iot.serialization.*;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;

import java.util.ArrayList;

/**
 * @author Oleksandr Havrylenko
 **/
public final class CustomSerdes {
    private CustomSerdes() {}
    public static Serde<Anomaly> Anomaly() {
        return Serdes.serdeFrom(new AnomalySerializer(), new AnomalyDeserializer());
    }

    public static Serde<PowerConsItem> PowerConsItem() {
        return Serdes.serdeFrom(new PowerConsItemSerializer(), new PowerConsItemDeserializer());
    }

    public static Serde<ArrayList<PowerConsItem>> ArrayListOfPowerConsItems() {
        return Serdes.serdeFrom(new ArrayListSerializer(), new ArrayListDeserializer());
    }

}
