package com.tsystems.project.validator;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.model.Ticket;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public abstract class Verification {

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    TrainService trainService;

    @Autowired
    TicketService ticketService;

    public boolean verifyTime(TrainDto trainDto) {
        return timeDifference(trainDto.getDepartureTime());
    }

    public boolean verifyTime(PassengerTrainDto passengerTrainDto) {
        return timeDifference(passengerTrainDto.getDepartureTime());
    }

    public boolean timeDifference(String departureTime) {
        long minutes =  ChronoUnit.MINUTES.between(LocalDateTime.now(),
                LocalDateTime.parse(timeConverter.reversedConvertDateTime(departureTime).toString()));
        return minutes <= 10;
    }

    public boolean verifySeats(TrainDto trainDto) {
        Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
        Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
        List<TrainDto> trains = trainService.getTrainsDtoBetweenTwoStations(trainDeparture, trainArrival);
        return !trains.stream().allMatch(t -> t.getSeats() > 0);
    }

    public boolean verifySeats(PassengerTrainDto passengerTrainDto) {
        TrainDto trainDto = new TrainDto();
        trainDto.setNumber(passengerTrainDto.getTrainNumber());
        trainDto.setOriginStation(passengerTrainDto.getOriginStation());
        trainDto.setDestinationStation(passengerTrainDto.getDestinationStation());
        return verifySeats(trainDto);
    }

    public boolean verifyPassenger(int trainNumber, PassengerDto passengerDto) {
        Ticket ticket = ticketService.getTicketByPassenger(trainNumber, passengerDto);
        return ticket != null;
    }
}
