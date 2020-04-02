package com.tsystems.project.converter;

import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrainConverter {
    private static final Log log = LogFactory.getLog(TrainConverter.class);

    @Bean
    public TrainConverter transferService() {
        return new TrainConverter();
    }
    public TrainDto convertToTrainDto(Train train) {
        TrainDto trainDto = null;
        try {
            trainDto = new TrainDto();
            trainDto.setId( train.getId());
            trainDto.setNumber(train.getNumber());
            trainDto.setOriginStation(train.getOriginStation());
            trainDto.setDestinationStation(train.getDestinationStation());
            trainDto.setDepartureTime(train.getSchedules().get(0).getDepartureTime());
            trainDto.setArrivalTime(train.getSchedules().get(1).getArrivalTime());
        } catch (NullPointerException e) {
            log.error(e.getCause());
        }
        return trainDto;
    }
}
