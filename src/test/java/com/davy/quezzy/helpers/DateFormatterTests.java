package com.davy.quezzy.helpers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DateFormatterTests {

    @Test
    public void testGetCurrentDateTimeNotNull() {
        String currentDateTime = DateFormatter.getCurrentDateTime();
        assertNotNull(currentDateTime, "The current date time should not be null");
    }

    @Test
    public void testGetCurrentDateTimeFormat() {
        String currentDateTime = DateFormatter.getCurrentDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        boolean isValidFormat = true;
        try {
            LocalDateTime.parse(currentDateTime, formatter);
        } catch (DateTimeParseException e) {
            isValidFormat = false;
        }
        assertTrue(isValidFormat, "The current date time should be in ISO_DATE_TIME format");
    }
}