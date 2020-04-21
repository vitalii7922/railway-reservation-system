package com.tsystems.project.validator;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.model.Station;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@Component
public class TrainValidator implements Validator {

    @Autowired
    StationService stationService;

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    TrainService trainService;

    private Log log = LogFactory.getLog(TrainValidator.class);

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Train.class.equals(aClass);
    }

    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        TrainDto trainDto = (TrainDto) o;
        Station from = stationService.getStationByName(trainDto.getOriginStation());
        Station to = stationService.getStationByName(trainDto.getDestinationStation());
        if (from != null && to != null && from.getId() == to.getId()) {
            errors.rejectValue("originStation", "same.stations",
                    "Origin and destination stations are the same");
        }
        try {
            LocalDateTime timeDeparture = LocalDateTime.parse(trainDto.getDepartureTime());
            LocalDateTime timeArrival = LocalDateTime.parse(trainDto.getArrivalTime());
            if (timeDeparture.isAfter(timeArrival)) {
                errors.rejectValue("departureTime", "incorrect.time.period", "Time departure is after time arrival");
            }
        } catch (DateTimeParseException e) {
            log.error(e);
        }
    }
}
