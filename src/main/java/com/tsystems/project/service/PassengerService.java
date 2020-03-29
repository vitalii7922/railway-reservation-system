package com.tsystems.project.service;

import com.tsystems.project.converter.PassengerConverter;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {
    @Autowired
    PassengerDao passengerDao;

    @Autowired
    TrainDao trainDao;

    @Autowired
    PassengerConverter passengerConverter;

    @Transactional
    public PassengerDto addPassenger(String firstName, String lastName, String birthDate) {
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
        passenger.setBirthDate(LocalDate.parse(birthDate));

        return passengerConverter.convertToPassengerDto(passengerDao.create(passenger));
    }

    @Transactional
    public PassengerDto getPassenger(String firstName, String lastName, String birthDate) {
        Passenger passenger = passengerDao.find(firstName, lastName, LocalDate.parse(birthDate));
        PassengerDto passengerDto = null;

        if (passenger != null) {
            passengerDto = passengerConverter.convertToPassengerDto(passenger);
        }

        return passengerDto;
    }
}
