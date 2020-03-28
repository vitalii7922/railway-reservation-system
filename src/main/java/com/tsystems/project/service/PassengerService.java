package com.tsystems.project.service;

import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PassengerService {
    @Autowired
    PassengerDao passengerDao;

    @Autowired
    TrainDao trainDao;

    @Transactional
    public Passenger addPassenger(int trainNumber, Station stationA, Station stationB, LocalDateTime departureTime, String firstName, String lastName, LocalDate birthDate) {
        Train trainDeparture = trainDao.findByStationId(stationA.getId());
        Train trainArrival = trainDao.findByStationId(stationB.getId());
        List<Train> trains = trainDao.findAllTrainsBetweenTwoStations(trainDeparture.getId(), trainArrival.getId());
        for (Train t : trains) {
            int seats = t.getSeats();
            seats--;
            t.setSeats(seats);
        }
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setSecondName(lastName);
        passenger.setBirthDate(birthDate);
        return passengerDao.create(passenger);
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
