package com.tsystems.project.service;

import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.domain.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {
    @Autowired
    private static PassengerDao passengerDao;

    public PassengerService() {
    }

    public void persist(Passenger entity) {
    }

    public void update(Passenger entity) {
    }

    public Passenger findById(long id) {
        return null;
    }

    public void delete(long id) {
    }

    public List<Passenger> findAll() {
        return null;
    }

    public void deleteAll() {
    }

    public PassengerDao passengerDao() {
        return passengerDao;
    }
}
