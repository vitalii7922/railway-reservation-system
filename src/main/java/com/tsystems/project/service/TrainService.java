package com.tsystems.project.service;

import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainService {
    @Autowired
    TrainDao trainDao;

    @Transactional
    public Train addTrain(Train train) {
        trainDao.create(train);
        return train;
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
