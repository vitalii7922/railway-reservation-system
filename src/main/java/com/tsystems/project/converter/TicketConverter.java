package com.tsystems.project.converter;

import com.tsystems.project.model.Ticket;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.TicketDto;
import org.springframework.stereotype.Component;

/**
 * author Vitalii Nefedov
 */
@Component
public class TicketConverter {

    private final TimeConverter timeConverter;

    public TicketConverter(TimeConverter timeConverter) {
        this.timeConverter = timeConverter;
    }

    /**
     * @param ticket         ticket
     * @param passengerDto   passenger data
     * @param trainDeparture departure time of a train
     * @param trainArrival   arrival time of a train
     * @return ticketDto
     */
    public TicketDto convertToTicketDto(Ticket ticket, PassengerDto passengerDto, Train trainDeparture, Train trainArrival) {
        TicketDto ticketDto = new TicketDto();
        if (ticket != null && passengerDto != null && trainArrival != null && trainDeparture != null) {
            ticketDto.setTrainNumber(trainDeparture.getNumber());
            ticketDto.setId(ticket.getId());
            ticketDto.setFirstName(passengerDto.getFirstName());
            ticketDto.setLastName(passengerDto.getSecondName());
            ticketDto.setBirthDate(passengerDto.getBirthDate());
            ticketDto.setStationOrigin(trainDeparture.getOriginStation().getName());
            ticketDto.setStationDeparture(trainArrival.getDestinationStation().getName());
            ticketDto.setDepartureTime(timeConverter.convertDateTime(trainDeparture.getSchedules().get(0).getDepartureTime()));
            ticketDto.setArrivalTime(timeConverter.convertDateTime(trainArrival.getSchedules().get(1).getArrivalTime()));
        }
        return ticketDto;
    }
}
