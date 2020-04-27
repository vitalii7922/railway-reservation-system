package com.tsystems.project.service;

import com.tsystems.project.converter.PassengerConverter;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.model.Passenger;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerLexicographicalOrder;
import com.tsystems.project.dto.PassengerTrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PassengerService {

    @Autowired
    PassengerDao passengerDao;

    @Autowired
    PassengerConverter passengerConverter;

    private static final Log log = LogFactory.getLog(PassengerService.class);

    @Transactional
    public PassengerDto addPassenger( PassengerTrainDto passengerTrainDto) {
        Passenger passenger = new Passenger();
        passenger.setFirstName(passengerTrainDto.getFirstName().toUpperCase());
        passenger.setSecondName(passengerTrainDto.getSecondName().toUpperCase());
        passenger.setBirthDate(LocalDate.parse(passengerTrainDto.getBirthDate()));
        log.info("----------passenger has been added-------------");
        return passengerConverter.convertToPassengerDto(passengerDao.create(passenger));
    }


    public PassengerDto getPassenger(@NotNull PassengerTrainDto passengerTrainDto) {
        PassengerDto passengerDto = null;
        Passenger passenger = passengerDao.findByPersonalData(passengerTrainDto.getFirstName(),
                passengerTrainDto.getSecondName(), LocalDate.parse(passengerTrainDto.getBirthDate()));
        if (passenger != null) {
            passengerDto = passengerConverter.convertToPassengerDtoAddDay(passenger);
        }
        return passengerDto;
    }

    public Passenger getPassengerById(long passengerId) {
        return passengerDao.findOne(passengerId);
    }

    public List<PassengerDto> getPassengers(int trainNumber) {
        Set<Passenger> passengerSet = new HashSet<>(passengerDao.findAllPassengersByTrainNumber(trainNumber));
        List<PassengerDto> passengersDto = new ArrayList<>();
        if (!CollectionUtils.isEmpty(passengerSet)) {
            passengersDto = passengerSet.stream()
                    .map(p -> passengerConverter.convertToPassengerDtoAddDay(p))
                    .collect(Collectors.toList());
            passengersDto.sort(new PassengerLexicographicalOrder());
        }
        return passengersDto;
    }
}
