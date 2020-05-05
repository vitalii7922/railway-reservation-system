package com.tsystems.project.service;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.model.Station;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.helper.TrainHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author Vitalii Nefedov
 */

@Service
public class TrainService {

    private final TrainDao trainDao;

    private final StationService stationService;

    private final TrainConverter trainConverter;

    private final ScheduleService scheduleService;

    private final TimeConverter timeConverter;

    private final TrainHelper trainHelper;

    private static final Log log = LogFactory.getLog(TrainService.class);

    public TrainService(TrainDao trainDao, StationService stationService, TrainConverter trainConverter,
                        ScheduleService scheduleService, TimeConverter timeConverter, TrainHelper trainHelper) {
        this.trainDao = trainDao;
        this.stationService = stationService;
        this.trainConverter = trainConverter;
        this.scheduleService = scheduleService;
        this.timeConverter = timeConverter;
        this.trainHelper = trainHelper;
    }

    /**
     * @param trainDto contains trainNumber, originStation model, destinationStation model
     * @return train model
     */
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
            log.info("--------Train number " + trainDto.getNumber()  + " has been added--------------");
        }

        return train;
    }

    /**
     * @param number train number
     * @return trainDto
     */
    @Transactional
    public TrainDto getTrainByNumber(int number) {
        Train train = trainDao.findByNumber(number);
        TrainDto trainDto = null;
        if (train != null) {
            trainDto = trainConverter.convertToTrainDto(train);
        }
        return trainDto;
    }

    /**
     * @param trainNumber train number
     * @return TrainStationDto contains train number, departure time, arrival time and station name
     */
    @Transactional
    public List<TrainStationDto> getTrainRoutByTrainNumber(int trainNumber) {
        List<Train> trains = trainDao.findTrainListByNumber(trainNumber);
        List<TrainDto> trainListDto = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trains)) {
            trainListDto = trains.stream()
                    .map(trainConverter::convertToTrainDto).collect(Collectors.toList());
        }
        return trainHelper.getTrainRout(trainListDto);
    }

    /**
     * @return trainsDto list of all trains
     */
    @Transactional
    public List<TrainDto> getTrainList() {
        List<Train> trains = trainDao.findAll();
        List<TrainDto> trainDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(trains)) {
            trainDtoList = trainHelper.getTrainListBetweenExtremeStations(trains);
            Collections.sort(trainDtoList);
        }
        return trainDtoList;
    }

    /**
     * validate departure time and arrival time and search trains between two points
     *
     * @param trainDto contains origin and destination stations, departure and arrival time
     * @return trainDtoList list of trains between two points
     */
    @Transactional
    public List<TrainDto> getTrainListBetweenTwoPoints(TrainDto trainDto) {
        List<TrainDto> trainDtoList = new ArrayList<>();
        if (trainDto.getDepartureTime().matches("\\d{2}-\\d{2}-\\d{4}\\s{1}\\d{2}:\\d{2}")) {
            trainDto.setDepartureTime(timeConverter.reversedConvertDateTime(trainDto.getDepartureTime()).toString());
            trainDto.setArrivalTime(timeConverter.reversedConvertDateTime(trainDto.getArrivalTime()).toString());
        }
        LocalDateTime departureTime = LocalDateTime.parse(trainDto.getDepartureTime());
        LocalDateTime arrivalTime = LocalDateTime.parse(trainDto.getArrivalTime());
        List<Train> trains;
        if (departureTime.isAfter(arrivalTime)) {
            return trainDtoList;
        }
        Station originStation = stationService.getStationByName(trainDto.getOriginStation());
        Station destinationStation = stationService.getStationByName(trainDto.getDestinationStation());
        if (originStation != null && destinationStation != null) {
            trains = trainDao.findByStationsIdAtGivenTerm(originStation.getId(),
                    destinationStation.getId(),
                    departureTime, arrivalTime);
            if (!CollectionUtils.isEmpty(trains)) {
                trainDtoList = trainHelper.searchTrainsBetweenTwoPoints(trains);
                Collections.sort(trainDtoList);
            }
        }
        return trainDtoList;
    }


    /**
     * @param trainDto contains train number and originStation model
     * @return trainDto
     */
    public Train getTrainByOriginStation(TrainDto trainDto) {
        return trainDao.findByDepartureStation(trainDto.getNumber(), trainDto.getOriginStation());
    }

    /**
     * @param trainDto contains train number and originStation model
     * @return train model
     */
    public Train getTrainByDestinationStation(TrainDto trainDto) {
        return trainDao.findByArrivalStation(trainDto.getNumber(), trainDto.getDestinationStation());
    }

    /**
     * @param trainDeparture contains train departure id
     * @param trainArrival   contains train arrival id
     * @return list of trains
     */
    @Transactional
    public List<TrainDto> getTrainListDtoByTrainsId(Train trainDeparture, Train trainArrival) {
        List<Train> trains = trainDao.findTrainListByTrainDepartureAndArrivalId(trainDeparture.getNumber(),
                trainDeparture.getId(), trainArrival.getId());
        return trains.stream().map(trainConverter::convertToTrainDto).collect(Collectors.toList());
    }

    public List<Train> getTrainListByTrainsId(Train trainDeparture, Train trainArrival) {
        return trainDao.findTrainListByTrainDepartureAndArrivalId(trainDeparture.getNumber(),
                trainDeparture.getId(), trainArrival.getId());
    }

    /**
     * @param train train model
     */
    public void updateTrain(Train train) {
        trainDao.update(train);
    }

    /**
     * @param originStation      origin station of a train
     * @param destinationStation destination station
     * @param departureTime      departure time of a train
     * @param arrivalTime        arrival time of a train
     * @return trainDto
     */
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

