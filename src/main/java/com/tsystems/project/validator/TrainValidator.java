package com.tsystems.project.validator;

import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.StationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * author Vitalii Nefedov
 */

@Component
public class TrainValidator implements Validator {

    private final StationService stationService;


    private Log log = LogFactory.getLog(TrainValidator.class);

    public TrainValidator(StationService stationService) {
        this.stationService = stationService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Train.class.equals(aClass);
    }

    /**
     * verify that origin and destination stations exist in DB, origin and destination stations are not the same
     * and time departure of a train is before or equal departure time
     *
     * @param o      object
     * @param errors errors
     */
    @Override
    public void validate(Object o, Errors errors) {
        TrainDto trainDto = (TrainDto) o;
        Station from = stationService.getStationByName(trainDto.getOriginStation());
        Station to = stationService.getStationByName(trainDto.getDestinationStation());
        if (from == null) {
            errors.rejectValue("originStation", "doesn't.exist",
                    "Origin station doesn't exist in DB");
        }

        if (to == null) {
            errors.rejectValue("destinationStation", "doesn't.exist",
                    "Destination station doesn't exist in DB");
        }

        if (from != null && to != null && from.getId() == to.getId()) {
            errors.rejectValue("originStation", "same.stations",
                    "Origin and destination stations are the same");
        }
        try {
            LocalDateTime timeDeparture = LocalDateTime.parse(trainDto.getDepartureTime());
            LocalDateTime timeArrival = LocalDateTime.parse(trainDto.getArrivalTime());
            if (timeDeparture.isAfter(timeArrival)) {
                errors.rejectValue("departureTime", "incorrect.time.period",
                        "Time departure is after time arrival");
            }
        } catch (DateTimeParseException e) {
            log.error(e);
            errors.rejectValue("departureTime", "incorrect.time.format",
                    "incorrect departure time or arrival time format");
        }
    }
}
