package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TrainService {
    @Autowired
    TrainDao trainDao;

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public Train addTrain(int trainNumber, Station originStation, Station destinationStation, String numberOfSeats) {
            trainDao.getCurrentSession().beginTransaction();
            Train trainDeparture;
            trainDeparture = new Train();
            trainDeparture.setNumber(trainNumber);
            trainDeparture.setOriginStation(originStation);
            trainDeparture.setDestinationStation(destinationStation);
            trainDeparture.setSeats(Integer.parseInt(numberOfSeats));
            Train train = trainDao.create(trainDeparture);
            trainDao.getCurrentSession().getTransaction().commit();
            trainDao.getCurrentSession().close();
            return train;
    }

    @Transactional
    public Train getTrainByNumber(int number) {
        trainDao.getCurrentSession().beginTransaction();
        Train train = trainDao.findByNumber(number);
        trainDao.getCurrentSession().getTransaction().commit();
        trainDao.getCurrentSession().close();
        return train;
    }

    @Transactional
    public List<String> getAllTrainsByNumbers(int trainNumber) {
        trainDao.getCurrentSession().beginTransaction();
        List<String> trains = trainDao.findByNumbers(trainNumber);
        trainDao.getCurrentSession().close();
        return trains;
    }

    @Transactional
    public List<TrainDto> getAllTrains() {
            trainDao.getCurrentSession().beginTransaction();
            List<Train> trains = trainDao.findAll();
            List<TrainDto> trainDtos = new ArrayList<>();
            Train lastTrain;
            for (int i = 0; i < trains.size(); i++) {
                TrainDto trainDto = modelMapper.map(trains.get(i), TrainDto.class);
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
                }
                trainDtos.add(trainDto);
            }
            trainDao.getCurrentSession().getTransaction().commit();
            trainDao.getCurrentSession().close();

        return trainDtos;
    }

    @Transactional
    public Map<Train, Train> getTrainsByStations(Station stationA, Station stationB, String timeDeparture, String timeArrival) {
        trainDao.getCurrentSession().beginTransaction();
        LocalDateTime departureTime = LocalDateTime.parse(timeDeparture);
        LocalDateTime arrivalTime = LocalDateTime.parse(timeArrival);
        Map<Train, Train> map = null;
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
        }

        trainDao.getCurrentSession().getTransaction().commit();
        trainDao.getCurrentSession().close();
        return map;
    }
}
