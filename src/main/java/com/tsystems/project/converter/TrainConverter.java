package com.tsystems.project.converter;

import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainConverter {
    @Bean
    public TrainConverter transferService() {
        return new TrainConverter();
    }
    public TrainDto convertToTrainDto(Train train) throws NullPointerException {
        TrainDto trainDto = new TrainDto();
        trainDto.setId( train.getId());
        trainDto.setNumber(train.getNumber());
        trainDto.setOriginStation(train.getOriginStation());
        trainDto.setDestinationStation(train.getDestinationStation());
        trainDto.setDepartureTime(train.getSchedules().get(0).getDepartureTime());
        trainDto.setArrivalTime(train.getSchedules().get(1).getArrivalTime());
        return trainDto;
    }
}
