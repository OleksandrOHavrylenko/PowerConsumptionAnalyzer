package com.example.iot.model;

import java.time.LocalDateTime;

/**
 * @author Oleksandr Havrylenko
 **/
public record Anomaly(LocalDateTime dateTime, String room, double peak) {
}
