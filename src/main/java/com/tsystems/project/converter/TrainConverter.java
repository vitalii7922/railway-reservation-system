package com.tsystems.project.converter;

import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import org.springframework.stereotype.Component;

/**
 * author Vitalii Nefedov
 */
@Component
public class TrainConverter {

    private final TimeConverter timeConverter;

    public TrainConverter(TimeConverter timeConverter) {
        this.timeConverter = timeConverter;
    }

    /**
     * @param train model
     * @return trainDto
     */
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
