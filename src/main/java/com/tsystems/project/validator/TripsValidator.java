package com.tsystems.project.validator;

import com.tsystems.project.model.Schedule;
import com.tsystems.project.model.Station;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;


/**
 * author Vitalii Nefedov
 */

@Component
public class TripsValidator implements Validator {
    private final StationService stationService;

    private final TrainService trainService;

    private final ScheduleService scheduleService;

    private Log log = LogFactory.getLog(TripsValidator.class);

    public TripsValidator(StationService stationService, TrainService trainService, ScheduleService scheduleService) {
        this.stationService = stationService;
        this.trainService = trainService;
        this.scheduleService = scheduleService;
    }

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Train.class.equals(aClass);
    }

    /**
     * verify that destination station exist in DB, destination station is not in a route
     * and time departure of a train is before or equal departure time
     *
     * @param o      object
     * @param errors errors
     */
    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        TrainDto trainDto = (TrainDto) o;
        try {
            LocalDateTime timeArrival = LocalDateTime.parse(trainDto.getArrivalTime());
            LocalDateTime timeDeparture = LocalDateTime.parse(trainDto.getDepartureTime());

            TrainDto train = trainService.getTrainByNumber(trainDto.getNumber());
            Schedule schedule = scheduleService.getScheduleByTrainId(train.getId());
            LocalDateTime arrivalTimeLastStation = schedule.getArrivalTime();
            List<TrainStationDto> trains = trainService.getTrainRoutByTrainNumber(trainDto.getNumber());

            if (timeDeparture.isAfter(timeArrival)) {
                errors.rejectValue("departureTime", "incorrect.time.period",
                        "Departure time is after arrival time");
            }

            if (arrivalTimeLastStation.isAfter(timeDeparture)) {
                errors.rejectValue("arrivalTime", "incorrect.time.period",
                        "Arrival time from last station is after departure time");
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
                        "destination station in a rout");
            }
        } catch (DateTimeParseException e) {
            log.error(e);
            errors.rejectValue("departureTime", "incorrect.time", "Incorrect departure or arrival time");
        }
    }
}
