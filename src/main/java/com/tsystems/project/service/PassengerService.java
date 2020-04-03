package com.tsystems.project.service;

import com.tsystems.project.converter.PassengerConverter;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.dto.PassengerDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    private static final Log log = LogFactory.getLog(PassengerService.class);

    @Transactional
    public PassengerDto addPassenger(String firstName, String lastName, LocalDate birthDate) {
        Passenger passenger = null;
        passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setSecondName(lastName);
        passenger.setBirthDate(birthDate);
        return passengerConverter.convertToPassengerDto(passengerDao.create(passenger));
    }

    @Transactional
    public PassengerDto getPassenger(String firstName, String lastName, LocalDate birthDate) {
        PassengerDto passengerDto = null;
            Passenger passenger = passengerDao.find(firstName, lastName, birthDate);
            if (passenger != null) {
            passengerDto = passengerConverter.convertToPassengerDto(passenger);
            }
        return passengerDto;
    }

    @Transactional
    public List<PassengerDto> getPassengers(int trainNumber) {
        List<Passenger> passengers = passengerDao.findAllPassengersByTrainNumber(trainNumber);
        List<PassengerDto> passengersDto = null;
        try {
            passengersDto = passengers.stream()
                    .map(p -> passengerConverter.convertToPassengerDto(p))
                    .collect(Collectors.toList());
        } catch (NullPointerException e) {
            log.error(e.getCause() + " List of passengers is empty");
        }
        return passengersDto;
    }
}
