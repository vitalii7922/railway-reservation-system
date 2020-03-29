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
    public Passenger addPassenger(String firstName, String lastName, LocalDate birthDate) {
        /*Train trainDeparture = trainDao.findByStationDepartureId(trainNumber, stationA.getId());
        Train trainArrival = trainDao.findByStationArrivalId(trainNumber, stationB.getId());
        List<Train> trains = trainDao.findAllTrainsBetweenTwoStations(trainDeparture.getId(), trainArrival.getId());

        if (trains.parallelStream().allMatch(t -> t.getSeats() > 0)) {
            for (Train t : trains) {
                int seats = t.getSeats();
                seats--;
                t.setSeats(seats);
            }
        }*/

        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setSecondName(lastName);
        passenger.setBirthDate(birthDate);
        return passengerDao.create(passenger);
    }


    public Passenger getPassenger(String firstName, String lastName, LocalDate birthDate) {
        return passengerDao.find(firstName, lastName, birthDate);
    }
}
