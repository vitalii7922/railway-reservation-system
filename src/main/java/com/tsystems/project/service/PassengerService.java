package com.tsystems.project.service;

import com.tsystems.project.converter.PassengerMapper;
import com.tsystems.project.dao.PassengerDao;
import com.tsystems.project.domain.Passenger;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerListOrdered;
import com.tsystems.project.dto.PassengerTrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 * author Vitalii Nefedov
 */
@Service
public class PassengerService {

    private final PassengerDao passengerDao;

    private final PassengerMapper passengerMapper;

    private static final Log log = LogFactory.getLog(PassengerService.class);

    public PassengerService(PassengerDao passengerDao, PassengerMapper passengerMapper) {
        this.passengerDao = passengerDao;
        this.passengerMapper = passengerMapper;
    }

    /**
     * @param passengerTrainDto contains firstName, secondName, birthDate
     * @return passengerDto
     */
    @Transactional
    public PassengerDto addPassenger(PassengerTrainDto passengerTrainDto) {
        Passenger passenger = Passenger.builder()
                .firstName(passengerTrainDto.getFirstName().toUpperCase())
                .secondName(passengerTrainDto.getSecondName().toUpperCase())
                .birthDate(LocalDate.parse(passengerTrainDto.getBirthDate()))
                .build();
        return passengerMapper.convertToPassengerDto(passengerDao.create(passenger));
    }

    /**
     * @param passengerTrainDto contains firstName, secondName, birthDate
     * @return passengerDto
     */
    public PassengerDto getPassenger(PassengerTrainDto passengerTrainDto) {
        PassengerDto passengerDto = null;
        Passenger passenger = passengerDao.findByPersonalData(passengerTrainDto.getFirstName(),
                passengerTrainDto.getSecondName(), LocalDate.parse(passengerTrainDto.getBirthDate()));
        if (passenger != null) {
            passengerDto = passengerMapper.convertToPassengerDtoAddDay(passenger);
        }
        return passengerDto;
    }

    /**
     * @param passengerId passenger id
     * @return passenger
     */
    public Passenger getPassengerById(long passengerId) {
        return passengerDao.findOne(passengerId);
    }

    /**
     * @param trainNumber train number
     * @return passengerDtoList
     */
    public List<PassengerDto> getPassengerListByTrainNumber(int trainNumber) {
        Set<Passenger> passengerSet = new HashSet<>(passengerDao.findAllPassengersByTrainNumber(trainNumber));
        List<PassengerDto> passengerDtoList = null;
        if (!CollectionUtils.isEmpty(passengerSet)) {
            passengerDtoList = passengerSet.stream()
                    .map(passengerMapper::convertToPassengerDtoAddDay)
                    .sorted(new PassengerListOrdered())
                    .collect(Collectors.toList());
        }
        return passengerDtoList;
    }
}
