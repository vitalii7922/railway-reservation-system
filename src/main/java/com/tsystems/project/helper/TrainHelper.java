package com.tsystems.project.helper;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.converter.TrainMapper;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author Vitalii Nefedov
 */
@Component
public class TrainHelper {

    private final TrainMapper trainMapper;

    private final TimeConverter timeConverter;

    private static final Log log = LogFactory.getLog(TrainHelper.class);

    public TrainHelper(TrainMapper trainMapper, TimeConverter timeConverter) {
        this.trainMapper = trainMapper;
        this.timeConverter = timeConverter;
    }

    /**
     * form list of train dto objects so that each train dto has first and last stations in a route and departure
     * and arrival time accordingly
     *
     * @param trains list of trains
     * @return trainDtoList
     */
    public List<TrainDto> getTrainListBetweenExtremeStations(List<Train> trains) {
        List<TrainDto> trainDtoList = new ArrayList<>();
        Set<Integer> trainNumberSet = new HashSet<>();
        Train lastTrain;
        for (int i = 0; i < trains.size(); i++) {
            lastTrain = null;
            if (trainNumberSet.contains(trains.get(i).getNumber())) {
                continue;
            }
            TrainDto trainDto = trainMapper.convertToTrainDto(trains.get(i));
            for (int j = i + 1; j < trains.size(); j++) { //find train departure and arrival with the same number
                if (trains.get(i).getNumber() == trains.get(j).getNumber()) {
                    lastTrain = trains.get(j);
                }
            }
            if (lastTrain != null) {
                trainDto.setDestinationStation(lastTrain.getDestinationStation().getName());
                trainDto.setArrivalTime(timeConverter.convertDateTime(lastTrain.getSchedules().get(1).getArrivalTime()));
            }
            trainDtoList.add(trainDto);
            trainNumberSet.add(trainDto.getNumber());
        }
        return trainDtoList;
    }

    /**
     * form list of train dto objects so that each train dto has first and last points in a route that are chosen
     * by a client at a given term
     *
     * @param trains list of trains
     * @return trainDtoList
     */
    public List<TrainDto> searchTrainsBetweenTwoPoints(List<Train> trains) {
        List<TrainDto> trainsDto = new ArrayList<>();
        try {
            for (int i = 0; i < trains.size(); i++) {
                Train departure = trains.get(i);
                TrainDto trainDto = trainMapper.convertToTrainDto(departure);
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

    /**
     * form train route
     *
     * @param trainDtoList trainDtoList
     * @return trainStationDtoList
     */
    public List<TrainStationDto> getTrainRout(List<TrainDto> trainDtoList) {
        String arrivalTime = null;
        List<TrainStationDto> trainStationDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trainDtoList)) {
            for (int i = 0; i < trainDtoList.size(); i++) {
                if (trainDtoList.size() == 1) {
                    //set only departure time and origin station
                    TrainStationDto trainStationDtoDeparture = TrainStationDto.builder()
                            .station(trainDtoList.get(i).getOriginStation())
                            .departureTime(trainDtoList.get(i).getDepartureTime())
                            .build();
                    //set arrival time and destination station
                    TrainStationDto trainStationDtoArrival = TrainStationDto.builder()
                            .station(trainDtoList.get(i).getDestinationStation())
                            .arrivalTime(trainDtoList.get(i).getArrivalTime())
                            .build();
                    trainStationDtoList.add(trainStationDtoDeparture);
                    trainStationDtoList.add(trainStationDtoArrival);
                } else if (i == trainDtoList.size() - 1) {
                    //set origin station and departure, arrival time
                    TrainStationDto trainStationDtoDeparture = TrainStationDto.builder()
                            .station(trainDtoList.get(i).getOriginStation())
                            .departureTime(trainDtoList.get(i).getDepartureTime())
                            .arrivalTime(arrivalTime)
                            .build();
                    //set destination station and arrival time
                    TrainStationDto trainStationDtoArrival = TrainStationDto.builder()
                            .arrivalTime(trainDtoList.get(i).getArrivalTime())
                            .station(trainDtoList.get(i).getDestinationStation())
                            .build();
                    trainStationDtoList.add(trainStationDtoDeparture);
                    trainStationDtoList.add(trainStationDtoArrival);
                } else {
                    //set origin station, departure and arrival time
                    TrainStationDto trainStationDto = TrainStationDto.builder()
                            .departureTime(trainDtoList.get(i).getDepartureTime())
                            .station(trainDtoList.get(i).getOriginStation())
                            .build();
                    if (arrivalTime != null) {
                        trainStationDto.setArrivalTime(arrivalTime);
                    }
                    arrivalTime = trainDtoList.get(i).getArrivalTime();
                    trainStationDtoList.add(trainStationDto);
                }
            }
        }
        return trainStationDtoList;
    }
}
