package com.tsystems.project.validator;

import com.tsystems.project.domain.Ticket;
import com.tsystems.project.dto.TrainDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TrainTicketValidator extends Verification implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Ticket.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TrainDto trainDto = (TrainDto) o;

        if (verifyTime(trainDto)) {
            errors.rejectValue("departureTime", "after.permitted.time",
                    "You cannot buy a ticket 10 minutes before time departure");
        }
        if (verifySeats(trainDto)) {
            errors.rejectValue("seats", "train.full",
                    "No free seats on train " + trainDto.getNumber());
        }
    }
}
