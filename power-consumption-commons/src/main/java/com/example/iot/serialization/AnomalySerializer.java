package com.example.iot.serialization;

import com.example.iot.model.Anomaly;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author Oleksandr Havrylenko
 **/
public class AnomalySerializer extends JsonSerializer<Anomaly> {
}
