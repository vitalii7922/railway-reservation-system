package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dto.TrainStationDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
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

    @Transactional
    public TrainDto addTrain(int trainNumber, Station originStation, Station destinationStation, String numberOfSeats) {
            Train trainDeparture;
            trainDeparture = new Train();
            trainDeparture.setNumber(trainNumber);
            trainDeparture.setOriginStation(originStation);
            trainDeparture.setDestinationStation(destinationStation);
            trainDeparture.setSeats(Integer.parseInt(numberOfSeats));
            trainDao.create(trainDeparture);
            TrainDto trainDto = modelMapper.map(trainDeparture, TrainDto.class);
            return trainDto;
    }

    @Transactional
    public TrainDto getTrainByNumber(int number) {
        Train train = trainDao.findByNumber(number);
        TrainDto trainDto = null;
        if (train != null) {
            trainDto = modelMapper.map(train, TrainDto.class);
        }
        return  trainDto;
    }

    @Transactional
    public List<TrainDto> getAllTrainsByNumbers(int trainNumber) {
        List<Train> trains = trainDao.findByNumbers(trainNumber);
        Type listType = new TypeToken<List<TrainDto>>() {}.getType();
        List<TrainDto> trainsDto = trains.stream()
                .map(t -> trainConverter.convertToTrainDto(t)).collect(Collectors.toList());
        return trainsDto;
    }

    @Transactional
    public List<TrainDto> getAllTrains() {
            List<Train> trains = trainDao.findAll();
            List<TrainDto> trainDtos = new ArrayList<>();
            Train lastTrain;
            for (int i = 0; i < trains.size(); i++) {
                TrainDto trainDto = trainConverter.convertToTrainDto(trains.get(i));
                lastTrain = null;
                if (trainDtos.stream().anyMatch(train -> train.getNumber() == trainDto.getNumber())) {
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
                trainDtos.add(trainDto);
            }

        return trainDtos;
    }

    @Transactional
    public List<TrainDto> getTrainsByStations(Station stationA, Station stationB, String timeDeparture, String timeArrival) {
        LocalDateTime departureTime = LocalDateTime.parse(timeDeparture);
        LocalDateTime arrivalTime = LocalDateTime.parse(timeArrival);
        /*Map<Train, Train> map = null;
        List<Train> trains = null;

        if (departureTime.isAfter(arrivalTime)){
            return map;
        }

        trains = trainDao.findByStations(stationA.getId(), stationB.getId(), departureTime, arrivalTime);
        map = new LinkedHashMap<>();

        if (trains != null) {
            for (int i = 0; i < trains.size(); i++) {
                for (int j = i + 1; j < trains.size(); j++) {
                    Train departure = trains.get(i);
                    Train arrive = trains.get(j);
                    if (departure.getNumber() == arrive.getNumber()) {
                        map.put(departure, arrive);
                    }
                }
            }
        }*/

        List<TrainDto> trainsDto = new ArrayList<>();
        List<Train> trains = null;
        if (departureTime.isAfter(arrivalTime)) {
            return trainsDto;
        }

        trains = trainDao.findByStations(stationA.getId(), stationB.getId(), departureTime, arrivalTime);

        if (trains != null) {
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
            }

        return trainsDto;
    }
}
