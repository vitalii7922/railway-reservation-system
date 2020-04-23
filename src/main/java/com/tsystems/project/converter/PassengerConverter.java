package com.tsystems.project.converter;

import com.tsystems.project.model.Passenger;
import com.tsystems.project.dto.PassengerDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class PassengerConverter {

    public PassengerDto convertToPassengerDto(Passenger passenger) {
        PassengerDto passengerDto = new PassengerDto();
        if (passenger != null) {
            passengerDto.setId(passenger.getId());
            passengerDto.setFirstName(passenger.getFirstName());
            passengerDto.setSecondName(passenger.getSecondName());
            passengerDto.setBirthDate(passenger.getBirthDate());
        }
        return passengerDto;
    }

    public PassengerDto convertToPassengerDtoAddDay(Passenger passenger) {
        PassengerDto passengerDto = new PassengerDto();
        if (passenger != null) {
            passengerDto.setId(passenger.getId());
            passengerDto.setFirstName(passenger.getFirstName());
            passengerDto.setSecondName(passenger.getSecondName());
            passengerDto.setBirthDate(passenger.getBirthDate().plusDays(1));
        }
        return passengerDto;
    }

}
