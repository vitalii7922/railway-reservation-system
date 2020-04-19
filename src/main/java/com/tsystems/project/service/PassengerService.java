package com.tsystems.project.service;

import com.tsystems.project.converter.PassengerConverter;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerLexicographicalOrder;
import com.tsystems.project.dto.PassengerTrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PassengerService {
    @Autowired
    PassengerDao passengerDao;

    @Autowired
    PassengerConverter passengerConverter;

    private static final Log log = LogFactory.getLog(PassengerService.class);

    @Transactional
    public PassengerDto addPassenger(PassengerTrainDto passengerTrainDto) {
        Passenger passenger = new Passenger();
        PassengerDto passengerDto = null;
        try {
            passenger.setFirstName(passengerTrainDto.getFirstName().toUpperCase());
            passenger.setSecondName(passengerTrainDto.getSecondName().toUpperCase());
            passenger.setBirthDate(LocalDate.parse(passengerTrainDto.getBirthDate()));
            passengerDto = passengerConverter.convertToPassengerDto(passengerDao.create(passenger));
        } catch (DateTimeParseException e) {
            e.getCause();
        }
        return passengerDto;
    }


    public PassengerDto getPassenger(PassengerTrainDto passengerTrainDto) {
        PassengerDto passengerDto = null;
        Passenger passenger = passengerDao.findByPersonalData(passengerTrainDto.getFirstName(), passengerTrainDto.getSecondName(),
                LocalDate.parse(passengerTrainDto.getBirthDate()));
        if (passenger != null) {
            passengerDto = passengerConverter.convertToPassengerDtoAddDay(passenger);
        }
        return passengerDto;
    }

    public Passenger getPassengerById(long passengerId) {
        return passengerDao.findOne(passengerId);
    }

    public List<PassengerDto> getPassengers(int trainNumber) {
        List<Passenger> passengers = passengerDao.findAllPassengersByTrainNumber(trainNumber);
        List<PassengerDto> passengersDto = null;
        try {
            passengersDto = passengers.stream()
                    .map(p -> passengerConverter.convertToPassengerDtoAddDay(p))
                    .collect(Collectors.toList());
            passengersDto.sort(new PassengerLexicographicalOrder());
        } catch (NullPointerException e) {
            log.error(e.getCause());
        }
        return passengersDto;
    }
}
