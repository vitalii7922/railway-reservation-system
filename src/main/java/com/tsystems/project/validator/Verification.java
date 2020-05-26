package com.tsystems.project.validator;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * author Vitalii Nefedov
 */

@Component
public abstract class Verification {

    private final TimeConverter timeConverter;

    private final TrainService trainService;

    private final TicketService ticketService;

    public Verification(TimeConverter timeConverter, TrainService trainService, TicketService ticketService) {
        this.timeConverter = timeConverter;
        this.trainService = trainService;
        this.ticketService = ticketService;
    }

    /**
     * @param trainDto contains departure time
     * @return true if train departure time less than 10 minutes than current time
     */
    public boolean verifyTimeDeparture(TrainDto trainDto) {
        return timeDifference(trainDto.getDepartureTime());
    }


    /**
     * @param passengerTrainDto contains departure time
     * @return true if train departure time more than 10 minutes than current time
     */
    public boolean verifyTimeDeparture(PassengerTrainDto passengerTrainDto) {
        return timeDifference(passengerTrainDto.getDepartureTime());
    }

    /**
     * @param departureTime train departure time
     * @return true if train departure time less than 10 minutes than current time
     */
    public boolean timeDifference(String departureTime) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(),
                LocalDateTime.parse(timeConverter.reversedConvertDateTime(departureTime).toString()));
        return minutes <= 10;
    }


    /**
     * @param trainDto contains origin station and destination station names
     * @return true if a train has free seats
     */
    public boolean verifyFreeSeats(TrainDto trainDto) {
        Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
        Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
        List<Train> trains = trainService.getTrainListByTrainsId(trainDeparture, trainArrival);
        return !trains.stream().allMatch(t -> t.getSeats() > 0);
    }

    /**
     * @param passengerTrainDto contains origin station and destination station names
     * @return true if a train has free seats
     */
    public boolean verifyFreeSeats(PassengerTrainDto passengerTrainDto) {
        TrainDto trainDto = new TrainDto();
        trainDto.setNumber(passengerTrainDto.getTrainNumber());
        trainDto.setOriginStation(passengerTrainDto.getOriginStation());
        trainDto.setDestinationStation(passengerTrainDto.getDestinationStation());
        return verifyFreeSeats(trainDto);
    }

    /**
     * @param trainDtoList list of trains
     * @param passengerDto passenger data
     * @return true if passenger doesn't have tickets on trains
     */
    public boolean verifyPassenger(List<TrainDto> trainDtoList, PassengerDto passengerDto) {
        for (TrainDto trainDto : trainDtoList) {
            if (ticketService.getTicketByPassenger(trainDto, passengerDto) != null) {
                return true;
            }
        }
        return false;
    }
}
