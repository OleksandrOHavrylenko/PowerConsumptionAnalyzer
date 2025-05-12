package com.example.iot.serialization;

import com.example.iot.model.Anomaly;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * @author Oleksandr Havrylenko
 **/
public class AnomalyDeserializer extends JsonDeserializer<Anomaly> {
}
