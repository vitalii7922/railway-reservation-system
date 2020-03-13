package com.tsystems.project.service;

import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PassengerService {
    @Autowired
    PassengerDao passengerDao;

    @Transactional
    public Passenger addPassenger(Passenger passenger) {
        passengerDao.create(passenger);
        return passenger;
    }

    @Transactional
    public Passenger editPassenger(Passenger passenger) throws RuntimeException {
        passengerDao.update(passenger);
        return passengerDao.findOne(passenger.getId());
    }

    @Transactional
    public void removePassenger(Passenger passenger) {
        passengerDao.delete(passenger);
    }

    public List<Schedule> getAllSchedules() {
        return passengerDao.findAll();
    }
}
