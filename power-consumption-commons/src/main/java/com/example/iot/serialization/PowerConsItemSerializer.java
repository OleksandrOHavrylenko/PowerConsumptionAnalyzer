package com.example.iot.serialization;

import com.example.iot.model.PowerConsItem;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 * @author Oleksandr Havrylenko
 **/
public class PowerConsItemSerializer extends JsonSerializer<PowerConsItem> {
}
