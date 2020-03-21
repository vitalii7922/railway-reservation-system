package com.tsystems.project.service;

import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TrainService {
    @Autowired
    TrainDao trainDao;

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
        Train train = trainDao.findByNumber(number);
        return train;
    }

    @Transactional
    public List<String> getAllTrainsByNumbers(int trainNumber) {
        return trainDao.findByNumbers(trainNumber);
    }


    @Transactional
    public List<Train> getAllTrains() {
        return trainDao.findAll();
    }

}
