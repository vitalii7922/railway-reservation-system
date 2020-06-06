package com.tsystems.project.converter;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * author Vitalii Nefedov
 */
@Component
public class TimeConverter {
    private static final String DATE_FORMATTER = "dd-MM-yyyy HH:mm";

    /**
     * convert format of date-time from "yyyy-MM-ddTHH:mm" to "dd-MM-yyyy HH:mm"
     *
     * @param localDateTime localDateTime
     * @return formatted local date-time("dd-MM-yyyy HH:mm")
     */
    public String convertDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
        return localDateTime.format(formatter);
    }

    /**
     * convert format of date-time from "dd-MM-yyyy HH:mm" to "yyyy-MM-ddTHH:mm"
     *
     * @param localDateTime localDateTime
     * @return formatted local date-time
     */
    public LocalDateTime reversedConvertDateTime(String localDateTime) {
        LocalDateTime time;
        int day = Integer.parseInt(localDateTime.substring(0, 2));
        int month = Integer.parseInt(localDateTime.substring(3, 5));
        int year = Integer.parseInt(localDateTime.substring(6, 10));
        int hours = Integer.parseInt(localDateTime.substring(11, 13));
        int minutes = Integer.parseInt(localDateTime.substring(14, 16));
        time = LocalDateTime.of(year, month, day, hours, minutes);
        return time;
    }

    public boolean isToday(String localDateTime) {
        return !reversedConvertDateTime(localDateTime).toLocalDate().equals(LocalDate.now());
    }
}
