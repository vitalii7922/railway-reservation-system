package com.tsystems.project.helper;

import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class TrainHelper {

    @Autowired
    TrainConverter trainConverter;

    private static final Log log = LogFactory.getLog(TrainHelper.class);

    @Bean
    public TrainHelper transferService() {
        return new TrainHelper();
    }

    public List<TrainDto> getTrainBetweenExtremeStations(List<Train> trains) {
        List<TrainDto> trainsDto = new ArrayList<>();
        Train lastTrain;
        try {
            for (int i = 0; i < trains.size(); i++) {
                TrainDto trainDto = trainConverter.convertToTrainDto(trains.get(i));
                lastTrain = null;
                if (trainsDto.stream().anyMatch(train -> train.getNumber() == trainDto.getNumber())) {
                    continue;
                }
                for (int j = i + 1; j < trains.size(); j++) {
                    if (trains.get(i).getNumber() == trains.get(j).getNumber() && trains.get(i).getId() < trains.get(j).getId()) {
                        lastTrain = trains.get(j);
                    }
                }
                if (lastTrain != null) {
                    trainDto.setDestinationStation(lastTrain.getDestinationStation());
                    trainDto.setArrivalTime(lastTrain.getSchedules().get(1).getArrivalTime());
                }
                trainsDto.add(trainDto);
            }
        } catch (NullPointerException e) {
            log.error(e.getCause());
        }
        return trainsDto;
    }

    public List<TrainDto> searchTrainBetweenExtremeStations(List<Train> trains) {
            List<TrainDto> trainsDto = new ArrayList<>();
            try {
                for (int i = 0; i < trains.size(); i++) {
                    Train departure = trains.get(i);
                    TrainDto trainDto = trainConverter.convertToTrainDto(departure);
                    if (trainsDto.stream().anyMatch(train -> train.getNumber() == trainDto.getNumber())) {
                        continue;
                    }
                    for (int j = i + 1; j < trains.size(); j++) {
                        Train arrive = trains.get(j);
                        if (departure.getNumber() == arrive.getNumber()) {
                            trainDto.setArrivalTime(arrive.getSchedules().get(1).getArrivalTime());
                            trainDto.setDestinationStation(arrive.getDestinationStation());
                            trainsDto.add(trainDto);
                        }
                    }
                }
            } catch (NullPointerException e) {
                log.error(e.getCause());
            }
        return trainsDto;
    }
}
