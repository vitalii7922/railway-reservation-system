package com.tsystems.project.converter;

import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.StationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainConverter {

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    StationService stationService;

    private static final Log log = LogFactory.getLog(TrainConverter.class);

    @Bean
    public TrainConverter transferService() {
        return new TrainConverter();
    }

    public TrainDto convertToTrainDto(Train train) {
        TrainDto trainDto = null;
        if (train != null) {
            trainDto = new TrainDto();
            trainDto.setId(train.getId());
            trainDto.setNumber(train.getNumber());
            trainDto.setOriginStation(train.getOriginStation().getName());
            trainDto.setDestinationStation(train.getDestinationStation().getName());
            trainDto.setSeats(train.getSeats());
            trainDto.setDepartureTime(timeConverter.convertDateTime(train.getSchedules().get(0).getDepartureTime()));
            trainDto.setArrivalTime(timeConverter.convertDateTime(train.getSchedules().get(1).getArrivalTime()));
        }
        return trainDto;
    }
}
