package com.tsystems.project.validator;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.model.Passenger;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.model.Train;
import com.tsystems.project.service.PassengerService;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * validate passenger data before booking a ticket
 *
 * author Vitalii Nefedov
 */
@Component
public class PassengerValidator extends Verification implements Validator {

    private final PassengerService passengerService;

    private final TrainService trainService;

    Pattern namePattern = Pattern.compile("[\\d|\\s*]", Pattern.CASE_INSENSITIVE);
    Pattern birthDate = Pattern.compile("\\d{4}.\\d{2}.\\d{2}", Pattern.CASE_INSENSITIVE);

    public PassengerValidator(TimeConverter timeConverter, TrainService trainService, TicketService ticketService,
                              PassengerService passengerService) {
        super(timeConverter, trainService, ticketService);
        this.passengerService = passengerService;
        this.trainService = trainService;
    }

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Passenger.class.equals(aClass);
    }

    /**
     * verify passenger data and departure time
     *
     * @param o      object
     * @param errors errors
     */
    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        PassengerTrainDto passengerTrainDto = (PassengerTrainDto) o;
        if (verifyFreeSeats(passengerTrainDto)) {
            errors.rejectValue("seats", "free.seats", "No free seats on train "
                    + passengerTrainDto.getTrainNumber());
        }
        if (verifyTimeDeparture(passengerTrainDto)) {
            errors.rejectValue("departureTime", "after.permitted.time",
                    "You cannot buy a ticket 10 minutes before time departure");
        }
        if (verifyInputFirstName(passengerTrainDto)) {
            errors.rejectValue("firstName", "incorrect.first.name",
                    "Incorrect first name");
        }
        if (verifyInputSecondName(passengerTrainDto)) {
            errors.rejectValue("secondName", "incorrect.second.name",
                    "Incorrect second name");
        }
        if (!birthDate.matcher(passengerTrainDto.getBirthDate()).matches()) {
            errors.rejectValue("birthDate", "incorrect.birth.date",
                    "Incorrect birth date");
        }
        if (!verifyInputFirstName(passengerTrainDto) && !verifyInputSecondName(passengerTrainDto) &&
                birthDate.matcher(passengerTrainDto.getBirthDate()).matches()) {
            PassengerDto passenger = passengerService.getPassenger(passengerTrainDto);
            TrainDto trainDto = new TrainDto();
            trainDto.setNumber(passengerTrainDto.getTrainNumber());
            trainDto.setOriginStation(passengerTrainDto.getOriginStation());
            trainDto.setDestinationStation(passengerTrainDto.getDestinationStation());
            Train trainDeparture = trainService.getTrainByOriginStation(trainDto);
            Train trainArrival = trainService.getTrainByDestinationStation(trainDto);
            List<TrainDto> trainList = trainService.getTrainListDtoByTrainsId(trainDeparture, trainArrival);
            if (passenger != null && verifyPassenger(trainList, passenger)) {
                errors.rejectValue("trainNumber", "booked.ticket",
                        "you have already bought a ticket on train " + passengerTrainDto.getTrainNumber());
            }
        }
    }

    /**
     * @param passengerTrainDto contains passenger first name
     * @return result of verification
     */
    public boolean verifyInputFirstName(PassengerTrainDto passengerTrainDto) {
        Matcher first = namePattern.matcher(passengerTrainDto.getFirstName());
        return first.find();
    }


    /**
     * @param passengerTrainDto contains passenger second name
     * @return result of verification
     */
    public boolean verifyInputSecondName(PassengerTrainDto passengerTrainDto) {
        Matcher second = namePattern.matcher(passengerTrainDto.getSecondName());
        return second.find();
    }
}
