package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

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
    public List<Train> getTrainsByStations(Station stationA, Station stationB, String timeDeparture, String timeArrival) {
        trainDao.getCurrentSession().beginTransaction();
        LocalDateTime departureTime = LocalDateTime.parse(timeDeparture);
        LocalDateTime arrivalTime = LocalDateTime.parse(timeArrival);
        List<Train> trains = null;

        if (departureTime.isAfter(arrivalTime)){
            return trains;
        }

        trains = trainDao.findByStations(stationA.getId(), stationB.getId(), departureTime, arrivalTime);

        Iterator<Train> trainIterator = trains.iterator();

        while (trainIterator.hasNext()) {
            Train t = trainIterator.next();
            if (t.getOriginStation().getId() == stationA.getId()) {
                if (departureTime.isBefore(scheduleDao.findByTrainDepartureId(t.getId()).getDepartureTime())) {
                    trainIterator.remove();
                }
                if (arrivalTime.isAfter(scheduleDao.findByTrainId(t.getId()).getArrivalTime())) {
                    trainIterator.remove();
                }
            }
        }
        trainDao.getCurrentSession().getTransaction().commit();
        trainDao.getCurrentSession().close();

        return trains;
    }
}
