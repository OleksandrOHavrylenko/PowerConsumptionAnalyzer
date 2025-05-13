package com.example.iot.serialization;

import com.example.iot.model.PowerConsItem;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author Oleksandr Havrylenko
 **/
public class ArrayListDeserializer implements Deserializer<ArrayList<PowerConsItem>> {

    private static final Logger logger = LoggerFactory.getLogger(ArrayListDeserializer.class);
    private ObjectMapper objectMapper = new ObjectMapper();

    public ArrayListDeserializer() {
        this.objectMapper.findAndRegisterModules();
    }

    @Override
    public ArrayList<PowerConsItem> deserialize(String s, byte[] data) {
        try {
            if (data == null){
                logger.info("Null received at deserializing ArrayList");
                return null;
            }
            logger.debug("Deserializing ArrayList object");
            return objectMapper.readValue(new String(data, "UTF-8"),  new TypeReference<ArrayList<PowerConsItem>>(){});
        } catch (Exception e) {
            logger.error("Error when deserializing byte[] to ArrayList object", e);
            throw new SerializationException("Error when deserializing byte[] to ArrayList object", e);
        }
    }
}
