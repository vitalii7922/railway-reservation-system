package com.tsystems.project.service;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.helper.TrainHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainService {

    @Autowired
    TrainDao trainDao;

    @Autowired
    StationService stationService;

    @Autowired
    TrainConverter trainConverter;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    TrainHelper trainHelper;

    private static final Log log = LogFactory.getLog(TrainService.class);

    @Transactional
    public Train addTrain(TrainDto trainDto) {
        Train trainDeparture;
        trainDeparture = new Train();
        trainDeparture.setNumber(trainDto.getNumber());
        trainDeparture.setOriginStation(stationService.getStationByName(trainDto.getOriginStation()));
        trainDeparture.setDestinationStation(stationService.getStationByName(trainDto.getDestinationStation()));
        trainDeparture.setSeats(trainDto.getSeats());
        Train train = trainDao.create(trainDeparture);
        if (train != null) {
            scheduleService.addSchedule(train, LocalDateTime.parse(trainDto.getDepartureTime()),
                    LocalDateTime.parse(trainDto.getArrivalTime()));
        }
        log.info("--------Train has been added--------------");
        return train;
    }

    @Transactional
    public TrainDto getTrainByNumber(int number) {
        Train train = trainDao.findByNumber(number);
        TrainDto trainDto = null;
        if (train != null) {
            trainDto = trainConverter.convertToTrainDto(train);
        }
        return trainDto;
    }

    @Transactional
    public List<TrainStationDto> getAllTrainsByNumber(int trainNumber) {
        List<Train> trains = trainDao.findTrainsByNumber(trainNumber);
        List<TrainDto> trainsDto = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trains)) {
            trainsDto = trains.stream()
                    .map(t -> trainConverter.convertToTrainDto(t)).collect(Collectors.toList());
        }
        return trainHelper.getTrainPath(trainsDto);
    }

    @Transactional
    public List<TrainDto> getAllTrains() {
        List<Train> trains = trainDao.findAll();
        List<TrainDto> trainsDto = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trains)) {
            trainsDto = trainHelper.getTrainsBetweenExtremeStations(trains);
            Collections.sort(trainsDto);
        }
        return trainsDto;
    }

    @Transactional
    public List<TrainDto> getTrainsByStations(TrainDto trainDto) {
        List<TrainDto> trainsDto = new ArrayList<>();
        if (trainDto.getDepartureTime().matches("\\d{2}-\\d{2}-\\d{4}\\s{1}\\d{2}:\\d{2}")) {
            trainDto.setDepartureTime(timeConverter.reversedConvertDateTime(trainDto.getDepartureTime()).toString());
            trainDto.setArrivalTime(timeConverter.reversedConvertDateTime(trainDto.getArrivalTime()).toString());
        }
        LocalDateTime departureTime = LocalDateTime.parse(trainDto.getDepartureTime());
        LocalDateTime arrivalTime = LocalDateTime.parse(trainDto.getArrivalTime());
        List<Train> trains;
        if (departureTime.isAfter(arrivalTime)) {
            return trainsDto;
        }
        trains = trainDao.findByStations(stationService.getStationByName(trainDto.getOriginStation()).getId(),
                stationService.getStationByName(trainDto.getDestinationStation()).getId(),
                departureTime, arrivalTime);
        if (!CollectionUtils.isEmpty(trains)) {
            trainsDto = trainHelper.searchTrainBetweenExtremeStations(trains);
            Collections.sort(trainsDto);
        }
        return trainsDto;
    }

    public Train getTrainByOriginStation(TrainDto trainDto) {
        return trainDao.findByStationDeparture(trainDto.getNumber(), trainDto.getOriginStation());
    }

    public Train getTrainByDestinationStation(TrainDto trainDto) {
        return trainDao.findByStationArrival(trainDto.getNumber(), trainDto.getDestinationStation());
    }

    @Transactional
    public List<TrainDto> getTrainsDtoBetweenTwoStations(Train trainDeparture, Train trainArrival) {
        List<Train> trains = trainDao.findAllTrainsBetweenTwoStations(trainDeparture.getId(), trainArrival.getId());
        return trains.stream().map(t -> trainConverter.convertToTrainDto(t)).collect(Collectors.toList());
    }

    public List<Train> getTrainsBetweenTwoStations(Train trainDeparture, Train trainArrival) {
        return trainDao.findAllTrainsBetweenTwoStations(trainDeparture.getId(), trainArrival.getId());
    }

    public void updateTrain(Train train) {
        trainDao.update(train);
    }

    public TrainDto initializeTrainDto(String originStation, String destinationStation, String departureTime,
                                       String arrivalTime) {
        TrainDto trainDto = new TrainDto();
        trainDto.setOriginStation(originStation);
        trainDto.setDestinationStation(destinationStation);
        trainDto.setDepartureTime(departureTime);
        trainDto.setArrivalTime(arrivalTime);
        trainDto.setAllTrainsDepartureTime(departureTime);
        trainDto.setAllTrainsArrivalTime(arrivalTime);
        return trainDto;
    }
}

