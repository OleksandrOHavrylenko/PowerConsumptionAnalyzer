package com.example.iot.serialization;

import com.example.iot.model.PowerConsItem;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * @author Oleksandr Havrylenko
 **/
public class PowerConsItemDeserializer extends JsonDeserializer<PowerConsItem> {
}
