package com.tsystems.project.converter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Configuration
public class TimeConverter {
    private static final String DATE_FORMATTER= "dd-MM-yyyy HH:mm";

    private static final Log log = LogFactory.getLog(TimeConverter.class);

    @Bean
    public TimeConverter transferService() {
        return new TimeConverter();
    }

    public String convertDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return localDateTime.format(formatter);
    }

    public LocalDateTime reversedConvertDateTime(String localDateTime) {
        LocalDateTime time = null;
        try {
            int day = Integer.parseInt(localDateTime.substring(0, 2));
            int month = Integer.parseInt(localDateTime.substring(3, 5));
            int year = Integer.parseInt(localDateTime.substring(6, 10));
            int hours = Integer.parseInt(localDateTime.substring(11, 13));
            int minutes = Integer.parseInt(localDateTime.substring(14, 16));
            time = LocalDateTime.of(year, month, day, hours, minutes);
        } catch (DateTimeParseException e) {
            log.error(e.getCause());
        }
        return time;
    }
}
