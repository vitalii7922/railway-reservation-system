package com.tsystems.project.converter;

import com.tsystems.project.domain.Passenger;
import com.tsystems.project.dto.PassengerDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PassengerConverter {
    @Bean
    public PassengerConverter transferService() {
        return new PassengerConverter();
    }
    public PassengerDto convertToPassengerDto(Passenger passenger) {
        PassengerDto passengerDto = new PassengerDto();
        passengerDto.setId(passenger.getId());
        passengerDto.setFirstName(passenger.getFirstName());
        passengerDto.setSecondName(passenger.getSecondName());
        passengerDto.setBirthDate(passenger.getBirthDate());
        return passengerDto;
    }

    public Passenger convertToPassenger(PassengerDto passengerDto) {
        Passenger passenger = new Passenger();
        passengerDto.setId(passengerDto.getId());
        passengerDto.setFirstName(passengerDto.getFirstName());
        passengerDto.setSecondName(passengerDto.getSecondName());
        passengerDto.setBirthDate(passengerDto.getBirthDate());
        return passenger;
    }
}
