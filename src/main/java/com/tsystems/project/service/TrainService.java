package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.ManyToOne;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class TrainService {
    @Autowired
    TrainDao trainDao;

    @Autowired
    ScheduleDao scheduleDao;

    @Transactional
    public Train addTrain(int trainNumber, Station originStation, Station destinationStation, String numberOfSeats) {
            Train trainDeparture;
            trainDeparture = new Train();
            trainDeparture.setNumber(trainNumber);
            trainDeparture.setOriginStation(originStation);
            trainDeparture.setDestinationStation(destinationStation);
            trainDeparture.setSeats(Integer.parseInt(numberOfSeats));
            return trainDao.create(trainDeparture);
    }

    @Transactional
    public Train editTrain(Train train) {
        trainDao.update(train);
        return trainDao.findOne(train.getId());
    }

    @Transactional
    public void removeTrain(Train train) {
        trainDao.delete(train);
    }

    @Transactional
    public Train getTrainByNumber(int number) {
        return trainDao.findByNumber(number);
    }

    @Transactional
    public List<String> getAllTrainsByNumbers(int trainNumber) {
        return trainDao.findByNumbers(trainNumber);
    }

    @Transactional
    public List<Train> getAllTrains() {
        return trainDao.findAll();
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
