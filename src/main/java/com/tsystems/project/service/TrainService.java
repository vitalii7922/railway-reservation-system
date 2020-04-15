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
    TrainConverter trainConverter;

    @Autowired
    TrainHelper trainHelper;

    private static final Log log = LogFactory.getLog(TrainService.class);

    @Transactional
    public TrainDto addTrain(int trainNumber, Station originStation, Station destinationStation, int numberOfSeats) {
            Train trainDeparture;
            trainDeparture = new Train();
            trainDeparture.setNumber(trainNumber);
            trainDeparture.setOriginStation(originStation);
            trainDeparture.setDestinationStation(destinationStation);
            trainDeparture.setSeats(numberOfSeats);
            Train train = trainDao.create(trainDeparture);
            return modelMapper.map(train, TrainDto.class);
    }

    @Transactional
    public TrainDto getTrainByNumber(int number) {
        Train train = trainDao.findByNumber(number);
        TrainDto trainDto = null;
        try {
            trainDto = modelMapper.map(train, TrainDto.class);
        } catch (Exception e) {
            log.error(e.getCause());
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
    public List<TrainDto> getTrainsByStations(Station stationA, Station stationB, String timeDeparture, String timeArrival) {
        List<TrainDto> trainsDto = new ArrayList<>();
        try {
            LocalDateTime departureTime = LocalDateTime.parse(timeDeparture);
            LocalDateTime arrivalTime = LocalDateTime.parse(timeArrival);
            List<Train> trains;
            if (departureTime.isAfter(arrivalTime)) {
                return trainsDto;
            }
            trains = trainDao.findByStations(stationA.getId(), stationB.getId(), departureTime, arrivalTime);

            trainsDto = trainHelper.searchTrainBetweenExtremeStations(trains);
            Collections.sort(trainsDto);

        } catch (NullPointerException | DateTimeParseException e) {
            log.error(e.getCause());
        }
        return trainsDto;
    }
}
