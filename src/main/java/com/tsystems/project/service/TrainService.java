package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TransferQueue;

@Service
public class TrainService {
    @Autowired
    TrainDao trainDao;

    @Transactional
    public Train addTrain(String trainNumber, Station originStation, Station destinationStation, String numberOfSeats) {

        Train trainArrive = trainDao.findByNumber(Integer.parseInt(trainNumber));
        Train trainDeparture = null;

        if (trainArrive != null && trainArrive.getDestinationStation().getId() != originStation.getId()) {
            return null;
        } else {
            trainDeparture = new Train();
            trainDeparture.setNumber(Integer.parseInt(trainNumber));
            trainDeparture.setOriginStation(originStation);
            trainDeparture.setDestinationStation(destinationStation);
            trainDeparture.setSeats(Integer.parseInt(numberOfSeats));
            return trainDao.create(trainDeparture);
        }
    }

    @Transactional
    public Train editTrain(Train train) throws RuntimeException {
        trainDao.update(train);
        return trainDao.findOne(train.getId());
    }

    @Transactional
    public void removeTrain(Train train) {
        trainDao.delete(train);
    }

    public List<Ticket> getAllTrains() {
        return trainDao.findAll();
    }
}
