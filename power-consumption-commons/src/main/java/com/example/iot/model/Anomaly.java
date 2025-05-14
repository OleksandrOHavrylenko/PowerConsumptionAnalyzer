package com.example.iot.model;

import java.time.LocalDateTime;

/**
 * @author Oleksandr Havrylenko
 **/
public record Anomaly(LocalDateTime date, String room, double peak) {
}
