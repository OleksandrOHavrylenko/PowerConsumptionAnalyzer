package com.example.iot.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Oleksandr Havrylenko
 **/
public record PowerConsItem(
        LocalDate date,
        LocalTime time,
        double globalActivePowerW,
        double globalReactivePowerW,
        double voltageV,
        double currentI,
        double subMetering1W,
        double subMetering2W,
        double subMetering3W
) {}
