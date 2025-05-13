package com.example.iot.serialization;

import com.example.iot.model.PowerConsItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author Oleksandr Havrylenko
 **/
public class ArrayListSerializer implements Serializer<ArrayList<PowerConsItem>> {
    private static final Logger logger = LoggerFactory.getLogger(ArrayListSerializer.class);
    ObjectMapper objectMapper = new ObjectMapper();

    public ArrayListSerializer() {
        this.objectMapper.findAndRegisterModules();
    }

    @Override
    public byte[] serialize(String s, ArrayList<PowerConsItem> data) {
        try {
            if (data == null){
                logger.info("Null received at serializing ArrayList object");
                return null;
            }
            logger.debug("Serializing ArrayList");
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            logger.error("Error when serializing ArrayList to byte[]", e);
            throw new SerializationException("Error when serializing ArrayList to byte[]");
        }
    }
}
