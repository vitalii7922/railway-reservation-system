package com.tsystems.project.service;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.converter.TrainMapper;
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

    private final TrainMapper trainMapper;

    private final ScheduleService scheduleService;

    private final TimeConverter timeConverter;

    private final TrainHelper trainHelper;

    private static final Log log = LogFactory.getLog(TrainService.class);

    public TrainService(TrainDao trainDao, StationService stationService, TrainMapper trainMapper,
                        ScheduleService scheduleService, TimeConverter timeConverter, TrainHelper trainHelper) {
        this.trainDao = trainDao;
        this.stationService = stationService;
        this.trainMapper = trainMapper;
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
        Train train = Train.builder()
                .number(trainDto.getNumber())
                .originStation(stationService.getStationByName(trainDto.getOriginStation()))
                .destinationStation(stationService.getStationByName(trainDto.getDestinationStation()))
                .seats(trainDto.getSeats())
                .build();
        train = trainDao.create(train);
        scheduleService.addSchedule(train, LocalDateTime.parse(trainDto.getDepartureTime()),
                LocalDateTime.parse(trainDto.getArrivalTime()));
        log.info("--------Train number " + trainDto.getNumber() + " has been added--------------");
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
            trainDto = trainMapper.convertToTrainDto(train);
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
        List<TrainDto> trainListDto = null;
        if (!CollectionUtils.isEmpty(trains)) {
            trainListDto = trains.stream()
                    .map(trainMapper::convertToTrainDto).collect(Collectors.toList());
        }
        return trainHelper.getTrainRout(trainListDto);
    }

    /**
     * @return trainsDtoList
     */
    @Transactional
    public List<TrainDto> getTrainList() {
        List<Train> trains = trainDao.findAll();
        List<TrainDto> trainDtoList = null;
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
     * @return trainDtoList
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
            trains = trainDao.findByStationIdAtGivenTerm(originStation.getId(), destinationStation.getId(),
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
     * @return train
     */
    public Train getTrainByDestinationStation(TrainDto trainDto) {
        return trainDao.findByArrivalStation(trainDto.getNumber(), trainDto.getDestinationStation());
    }

    /**
     * @param trainDeparture contains train departure id
     * @param trainArrival   contains train arrival id
     * @return trainList
     */
    @Transactional
    public List<TrainDto> getTrainListDtoByTrainsId(Train trainDeparture, Train trainArrival) {
        List<Train> trains = trainDao.findTrainListByTrainDepartureAndArrivalId(trainDeparture.getNumber(),
                trainDeparture.getId(), trainArrival.getId());
        return trains.stream().map(trainMapper::convertToTrainDto).collect(Collectors.toList());
    }

    public List<Train> getTrainListByTrainsId(Train trainDeparture, Train trainArrival) {
        return trainDao.findTrainListByTrainDepartureAndArrivalId(trainDeparture.getNumber(),
                trainDeparture.getId(), trainArrival.getId());
    }

    /**
     * @param train train
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

