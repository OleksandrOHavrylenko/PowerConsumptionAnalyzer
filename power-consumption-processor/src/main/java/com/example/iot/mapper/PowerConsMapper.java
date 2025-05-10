package com.example.iot.mapper;

import com.example.iot.model.PowerConsItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Oleksandr Havrylenko
 **/
public class PowerConsMapper implements ModelMapper<PowerConsItem>{
    private static final Logger logger = LoggerFactory.getLogger(PowerConsMapper.class);

    @Override
    public PowerConsItem parseLine(final String nextLine) {
        String[] splittedData = nextLine.split(";");
        if (splittedData.length != 9) {
            logger.error("Invalid data line: size={}, line: {}, ", splittedData.length, nextLine);
            throw new RuntimeException(String.format("Invalid data line: size=%s, line: %s, ", splittedData.length, nextLine));
        } else {
            LocalDate date = null;
            LocalTime time = null;
            double globalActivePowerW = 0;
            double globalReactivePowerW = 0;
            double voltageV = 0;
            double currentI = 0;
            double subMetering1W = 0;
            double subMetering2W = 0;
            double subMetering3W = 0;
            try {
                date = LocalDate.parse(splittedData[0]);
                time = LocalTime.parse(splittedData[1]);
                globalActivePowerW = Double.parseDouble(splittedData[2]) * 1000;
                globalReactivePowerW = Double.parseDouble(splittedData[3]) * 1000;
                voltageV = Double.parseDouble(splittedData[4]);
                currentI = Double.parseDouble(splittedData[5]);
                subMetering1W = Double.parseDouble(splittedData[6]);
                subMetering2W = Double.parseDouble(splittedData[7]);
                subMetering3W = Double.parseDouble(splittedData[8]);
                return new PowerConsItem(date, time, globalActivePowerW, globalReactivePowerW,
                        voltageV, currentI, subMetering1W, subMetering2W, subMetering3W);
            } catch (NumberFormatException e) {
                logger.error("Error during parsing data line={} ", nextLine, e);
                throw new RuntimeException(e);
            }
        }
    }
}
