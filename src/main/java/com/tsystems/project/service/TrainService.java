package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.helper.TrainHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrainService {

    @Autowired
    TrainDao trainDao;

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    StationService stationService;

    @Autowired
    TrainConverter trainConverter;

    @Autowired
    TrainHelper trainHelper;

    private static final Log log = LogFactory.getLog(TrainService.class);

    @Transactional
    public TrainDto addTrain(TrainDto trainDto) {
            Train trainDeparture;
            trainDeparture = new Train();
            trainDeparture.setNumber(trainDto.getNumber());
            trainDeparture.setOriginStation(stationService.getStationByName(trainDto.getOriginStation()));
            trainDeparture.setDestinationStation(stationService.getStationByName(trainDto.getDestinationStation()));
            trainDeparture.setSeats(trainDto.getSeats());
            Train train = trainDao.create(trainDeparture);
            return trainConverter.convertToTrainDto(train);
    }

    @Transactional
    public TrainDto getTrainByNumber(int number) {
        Train train = trainDao.findByNumber(number);
        TrainDto trainDto = null;
        if (train != null) {
            trainDto = trainConverter.convertToTrainDto(train);
        }
        return  trainDto;
    }

    @Transactional
    public List<TrainStationDto> getAllTrainsByNumber(int trainNumber) {
        List<Train> trains = trainDao.findTrainsByNumber(trainNumber);
        List<TrainDto> trainsDto = new ArrayList<>();
        try {
             trainsDto = trains.stream()
                    .map(t -> trainConverter.convertToTrainDto(t)).collect(Collectors.toList());
        } catch (Exception e) {
            log.error(e.getCause());
        }

        return trainHelper.getTrainPath(trainsDto);
    }

    @Transactional
    public List<TrainDto> getAllTrains() {
            List<Train> trains = trainDao.findAll();
            List<TrainDto> trainsDto = new ArrayList<>();
            try {
                trainsDto = trainHelper.getTrainBetweenExtremeStations(trains);
                Collections.sort(trainsDto);
            } catch (NullPointerException e) {
                log.error(e.getCause());
            }
        return trainsDto;
    }

    @Transactional
    public List<TrainDto> getTrainsByStations(TrainDto trainDto) {
        List<TrainDto> trainsDto = new ArrayList<>();
        try {
            LocalDateTime departureTime = LocalDateTime.parse(trainDto.getDepartureTime());
            LocalDateTime arrivalTime = LocalDateTime.parse(trainDto.getArrivalTime());
            List<Train> trains;
            if (departureTime.isAfter(arrivalTime)) {
                return trainsDto;
            }
            trains = trainDao.findByStations(stationService.getStationByName(trainDto.getOriginStation()).getId(),
                    stationService.getStationByName(trainDto.getDestinationStation()).getId(),
                    departureTime, arrivalTime);

            trainsDto = trainHelper.searchTrainBetweenExtremeStations(trains);
            Collections.sort(trainsDto);

        } catch (NullPointerException | DateTimeParseException e) {
            log.error(e.getCause());
        }
        return trainsDto;
    }
}
