package com.tsystems.project.helper;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author Vitalii Nefedov
 */
@Component
public class TrainHelper {

    private final TrainConverter trainConverter;

    private final TimeConverter timeConverter;

    private static final Log log = LogFactory.getLog(TrainHelper.class);

    public TrainHelper(TrainConverter trainConverter, TimeConverter timeConverter) {
        this.trainConverter = trainConverter;
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
        Train lastTrain;
        for (int i = 0; i < trains.size(); i++) {
            TrainDto trainDto = trainConverter.convertToTrainDto(trains.get(i));
            lastTrain = null;
            if (trainDtoList.stream().anyMatch(train -> train.getNumber() == trainDto.getNumber())) {
                continue;
            }
            for (int j = i + 1; j < trains.size(); j++) { //find train departure and arrival with the same number
                if (trains.get(i).getNumber() == trains.get(j).getNumber() && trains.get(i).getId() < trains.get(j).getId()) {
                    lastTrain = trains.get(j);
                }
            }
            if (lastTrain != null) {
                trainDto.setDestinationStation(lastTrain.getDestinationStation().getName());
                trainDto.setArrivalTime(timeConverter.convertDateTime(lastTrain.getSchedules().get(1).getArrivalTime()));
            }
            trainDtoList.add(trainDto);
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
                    TrainStationDto trainStationDtoDeparture = new TrainStationDto();
                    trainStationDtoDeparture.setStation(trainDtoList.get(i).getOriginStation());
                    trainStationDtoDeparture.setDepartureTime(trainDtoList.get(i).getDepartureTime());
                    //set arrival time and destination station
                    TrainStationDto trainStationDtoArrival = new TrainStationDto();
                    trainStationDtoArrival.setArrivalTime(trainDtoList.get(i).getArrivalTime());
                    trainStationDtoArrival.setStation(trainDtoList.get(i).getDestinationStation());
                    trainStationDtoList.add(trainStationDtoDeparture);
                    trainStationDtoList.add(trainStationDtoArrival);
                } else if (i == trainDtoList.size() - 1) {
                    //set origin station and departure, arrival time
                    TrainStationDto trainStationDtoDeparture = new TrainStationDto();
                    trainStationDtoDeparture.setStation(trainDtoList.get(i).getOriginStation());
                    trainStationDtoDeparture.setDepartureTime(trainDtoList.get(i).getDepartureTime());
                    trainStationDtoDeparture.setArrivalTime(arrivalTime);
                    //set destination station and arrival time
                    TrainStationDto trainStationDtoArrival = new TrainStationDto();
                    trainStationDtoArrival.setStation(trainDtoList.get(i).getDestinationStation());
                    trainStationDtoArrival.setArrivalTime(trainDtoList.get(i).getArrivalTime());
                    trainStationDtoList.add(trainStationDtoDeparture);
                    trainStationDtoList.add(trainStationDtoArrival);
                } else {
                    //set origin station, departure and arrival time
                    TrainStationDto trainStationDto = new TrainStationDto();
                    trainStationDto.setStation(trainDtoList.get(i).getOriginStation());
                    trainStationDto.setDepartureTime(trainDtoList.get(i).getDepartureTime());
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
