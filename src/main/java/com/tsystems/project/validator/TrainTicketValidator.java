package com.tsystems.project.validator;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.model.Ticket;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * validate free seats in a train and departure time
 * <p>
 * author Vitalii Nefedov
 */

@Component
public class TrainTicketValidator extends Verification implements Validator {

    public TrainTicketValidator(TimeConverter timeConverter, TrainService trainService, TicketService ticketService) {
        super(timeConverter, trainService, ticketService);
    }

    @Override
    public boolean supports(@NotNull Class<?> aClass) {
        return Ticket.class.equals(aClass);
    }

    /**
     * @param o      object
     * @param errors errors
     */
    @Override
    public void validate(@NotNull Object o, @NotNull Errors errors) {
        TrainDto trainDto = (TrainDto) o;

        if (verifyTimeDeparture(trainDto)) {
            errors.rejectValue("departureTime", "after.permitted.time",
                    "You cannot buy a ticket 10 minutes before time departure");
        }
        if (verifyFreeSeats(trainDto)) {
            errors.rejectValue("seats", "train.full",
                    "No free seats on train " + trainDto.getNumber());
        }
    }
}
