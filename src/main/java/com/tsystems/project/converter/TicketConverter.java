package com.tsystems.project.converter;

import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.TicketDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConverter {
    @Bean
    public TicketConverter transferService() {
        return new TicketConverter();
    }

    public TicketDto convertToTicketDto(Ticket t, PassengerDto passengerDto, Train trainDeparture, Train trainArrival) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setTrainNumber(trainDeparture.getNumber());
        ticketDto.setId(t.getId());
        ticketDto.setFirstName(passengerDto.getFirstName());
        ticketDto.setLastName(passengerDto.getSecondName());
        ticketDto.setBirthDate(passengerDto.getBirthDate());
        ticketDto.setStationOrigin(trainDeparture.getOriginStation().getName());
        ticketDto.setStationDeparture(trainArrival.getDestinationStation().getName());
        ticketDto.setDepartureTime(trainDeparture.getSchedules().get(0).getDepartureTime());
        ticketDto.setArrivalTime(trainArrival.getSchedules().get(1).getArrivalTime());
        return ticketDto;
    }
}
