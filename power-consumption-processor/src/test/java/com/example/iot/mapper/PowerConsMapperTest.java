package com.example.iot.mapper;

import com.example.iot.model.PowerConsItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oleksandr Havrylenko
 **/
class PowerConsMapperTest {

    private ModelMapper<PowerConsItem> mapper = new PowerConsMapper();

    @Test
    void correctLineTest() {
        final String inputLine = "16/12/2006;17:24:00;4.216;0.418;234.840;18.400;0.000;1.000;17.000";

        PowerConsItem expected = new PowerConsItem(
                LocalDate.of(2006, 12, 16),
                LocalTime.of(17, 24, 0),
                4216.0,
                418.0,
                234.84,
                18.4,
                0.0,
                1.0,
                17.0);
        PowerConsItem actual = mapper.parseLine(inputLine);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void invalidLineLengthTest() {
        final String inputLine = "16/12/2006;17:24:00;?;?;?;18.400;0.000;1.000";


        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> mapper.parseLine(inputLine),
                "Expected RuntimeException due to invalid line length");

        assertTrue(actualException.getMessage().contains("Invalid data line: size=8"));
    }

    @Test
    void invalidLineParseValueTest() {
        final String inputLine = "16/12/2006;17:24:00;?;?;?;18.400;0.000;1.000;?";


        RuntimeException actualException = assertThrows(RuntimeException.class,
                () -> mapper.parseLine(inputLine),
                "Expected RuntimeException due to invalid line parsing");

        assertTrue(actualException.getMessage().contains("Error during parsing data line="));
    }

}