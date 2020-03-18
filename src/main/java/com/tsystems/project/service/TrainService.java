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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TransferQueue;

@Service
public class TrainService {
    @Autowired
    TrainDao trainDao;

    @Autowired
    StationService stationService;

    @Transactional
    public Train addTrain(String trainNumber, Station originStation, Station destinationStation, String numberOfSeats) {
        Train train = new Train();
        train.setNumber(Integer.parseInt(trainNumber));
        train.setOriginStation(originStation);
        train.setDestinationStation(destinationStation);
        train.setSeats(Integer.parseInt(numberOfSeats));

       /* stationService.editStation(originStation, train);
        stationService.editStation(destinationStation, train);*/

        return trainDao.create(train);
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
