package com.tsystems.project.validator;

import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;


@Component
public class TripsValidator implements Validator {
    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    ScheduleService scheduleService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Train.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TrainDto trainDto = (TrainDto) o;
        try {
            LocalDateTime timeArrival = LocalDateTime.parse(trainDto.getArrivalTime());
            LocalDateTime timeDeparture = LocalDateTime.parse(trainDto.getDepartureTime());

            TrainDto train = trainService.getTrainByNumber(trainDto.getNumber());
            Schedule schedule = scheduleService.getScheduleByTrainId(train.getId());
            LocalDateTime time = schedule.getArrivalTime();
            List<TrainStationDto> trains = trainService.getAllTrainsByNumber(trainDto.getNumber());

            if (timeDeparture.isAfter(timeArrival)) {
                errors.rejectValue("departureTime", "incorrect.time.period",
                        "Departure time is after arrival time");
            }

            if (time.isAfter(timeDeparture)) {
                errors.rejectValue("arrivalTime", "incorrect.time.period",
                        "Arrival time is after departure time");
            }

            Station from = stationService.getStationByName(train.getDestinationStation());
            Station to = stationService.getStationByName(trainDto.getDestinationStation());

            if (from == null) {
                errors.rejectValue("originStation", "incorrect.station.name",
                        "Origin station is empty line or doesn't" +
                        " exist in DB");
            }

            if (to == null) {
                errors.rejectValue("destinationStation", "incorrect.station.name", "Destination" +
                        " station is empty line or doesn't exist in DB");
            }

            if (to != null && trains.stream().anyMatch(t -> t.getStation().equalsIgnoreCase(to.getName()))) {
                errors.rejectValue("destinationStation", "incorrect.station.path",
                        "destination station in a path");
            }
        } catch (DateTimeParseException e) {
            e.getErrorIndex();
        }
    }
}
