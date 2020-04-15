package com.tsystems.project.validator;

import com.tsystems.project.domain.Station;
import com.tsystems.project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class TrainValidator {

    @Autowired
    StationService stationService;

    @Bean
    public TrainValidator transferService() {
        return new TrainValidator();
    }

    public String validateTrainData(String originStation, String destinationStation, String arrivalTime, String departureTime) {
        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);

        Station from = stationService.getStationByName(originStation);
        Station to = stationService.getStationByName(destinationStation);

        if (from == null || to == null) {
            return  "you haven't added station or this station doesn't exist in DB";
        }

        if (from.getId() == to.getId()) {
            return  "you haven't added station or this station doesn't exist in DB";
        }

        if (timeDeparture.isAfter(timeArrival)) {
            return  "you haven't added station or this station doesn't exist in DB";
        }

        return null;
    }
}
