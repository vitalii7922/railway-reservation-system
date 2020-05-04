package com.tsystems.project.helper;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.StationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class TrainHelper {

    @Autowired
    TrainConverter trainConverter;

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    StationService stationService;

    private static final Log log = LogFactory.getLog(TrainHelper.class);


    public List<TrainDto> getTrainListBetweenExtremeStations(List<Train> trains) {
        List<TrainDto> trainsDto = new ArrayList<>();
        Train lastTrain;
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
                trainDto.setDestinationStation(lastTrain.getDestinationStation().getName());
                trainDto.setArrivalTime(timeConverter.convertDateTime(lastTrain.getSchedules().get(1).getArrivalTime()));
            }
            trainsDto.add(trainDto);
        }
        return trainsDto;
    }

    public List<TrainDto> searchTrainsBetweenTwoPoints(List<Train> trains) {
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
                    if (departure.getNumber() == arrive.getNumber() && departure.getId() <= arrive.getId()) {
                        trainDto.setArrivalTime(timeConverter.convertDateTime(arrive.getSchedules().get(1).getArrivalTime()));
                        trainDto.setDestinationStation(arrive.getDestinationStation().getName());
                        trainsDto.add(trainDto);
                    }
                }
            }
        } catch (NullPointerException e) {
            log.error(e.getCause());
        }
        return trainsDto;
    }

    public List<TrainStationDto> getTrainRout(List<TrainDto> trainsDto) {
        String arrivalTime = null;
        List<TrainStationDto> trainStationDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trainsDto)) {
            for (int i = 0; i < trainsDto.size(); i++) {
                if (trainsDto.size() == 1) {
                    TrainStationDto trainStationDtoDeparture = new TrainStationDto();
                    trainStationDtoDeparture.setStation(trainsDto.get(i).getOriginStation());
                    trainStationDtoDeparture.setDepartureTime(trainsDto.get(i).getDepartureTime());
                    TrainStationDto trainStationDtoArrival = new TrainStationDto();
                    trainStationDtoArrival.setArrivalTime(trainsDto.get(i).getArrivalTime());
                    trainStationDtoArrival.setStation(trainsDto.get(i).getDestinationStation());
                    trainStationDtoList.add(trainStationDtoDeparture);
                    trainStationDtoList.add(trainStationDtoArrival);
                } else if (i == trainsDto.size() - 1) {
                    TrainStationDto trainStationDto1 = new TrainStationDto();
                    trainStationDto1.setStation(trainsDto.get(i).getOriginStation());
                    trainStationDto1.setDepartureTime(trainsDto.get(i).getDepartureTime());
                    trainStationDto1.setArrivalTime(arrivalTime);
                    TrainStationDto trainStationDto2 = new TrainStationDto();
                    trainStationDto2.setStation(trainsDto.get(i).getDestinationStation());
                    trainStationDto2.setArrivalTime(trainsDto.get(i).getArrivalTime());
                    trainStationDtoList.add(trainStationDto1);
                    trainStationDtoList.add(trainStationDto2);
                } else {
                    TrainStationDto trainStationDto = new TrainStationDto();
                    trainStationDto.setStation(trainsDto.get(i).getOriginStation());
                    trainStationDto.setDepartureTime(trainsDto.get(i).getDepartureTime());
                    if (arrivalTime != null) {
                        trainStationDto.setArrivalTime(arrivalTime);
                    }
                    arrivalTime = trainsDto.get(i).getArrivalTime();
                    trainStationDtoList.add(trainStationDto);
                }
            }
        }
        return trainStationDtoList;
    }
}
