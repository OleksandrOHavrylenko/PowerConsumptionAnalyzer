package com.example.iot.extractor;

import com.example.iot.mapper.ModelMapper;
import com.example.iot.mapper.PowerConsMapper;
import com.example.iot.model.PowerConsItem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author Oleksandr Havrylenko
 **/
public class PowerConsTimestampExtractor implements TimestampExtractor {

    @Override
    public long extract(ConsumerRecord<Object, Object> record, long partitionTime) {
        try {
            final String line = (String)record.value();
            String[] splittedData = line.split(";");
            final LocalDate date = LocalDate.parse(splittedData[0], DateTimeFormatter.ofPattern("d/M/yyyy"));
            final LocalTime time = LocalTime.parse(splittedData[1]);
            final LocalDateTime localDateTime = LocalDateTime.of(date, time);
            return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        } catch (Exception e) {
            return record.timestamp();
        }
    }
}
