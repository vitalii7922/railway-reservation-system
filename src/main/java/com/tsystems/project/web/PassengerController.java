package com.tsystems.project.web;

import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.service.PassengerService;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.TicketValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Controller
public class PassengerController {

    @Autowired
    TicketService ticketService;

    @Autowired
    PassengerService passengerService;

    @Autowired
    TicketValidator ticketValidator;

    @Autowired
    TrainService trainService;

    private static final String MESSAGE = "message";

    private static final Log log = LogFactory.getLog(PassengerController.class);

    @ResponseBody
    @GetMapping(value = "/addPassengerTicket")
    public ModelAndView addPassenger(@RequestParam("trainNumber") int trainNumber,
                                     @RequestParam("stationA") long stationAId,
                                     @RequestParam("stationB") long stationBId,
                                     @RequestParam("first_name") String firstName,
                                     @RequestParam("last_name") String lastName,
                                     @RequestParam("date_of_birth") String birthDate,
                                     ModelAndView model) {
        PassengerDto passenger;
        model.setViewName("passenger.jsp");
        if (!ticketValidator.verifySeats(trainNumber, stationAId, stationBId)) {
            model.addObject(MESSAGE, "no free seats on train " + trainNumber);
            return model;
        }
        if (firstName.matches("\\s*") || lastName.matches("\\s*")) {
            model.addObject(MESSAGE, "you added incorrect data");
            return model;
        }
        passenger = passengerService.getPassenger(firstName, lastName, LocalDate.parse(birthDate));
        if (passenger == null) {
            passenger = passengerService.addPassenger(firstName, lastName, LocalDate.parse(birthDate));
        }
        if (!ticketValidator.verifyPassenger(trainNumber, passenger)) {
            model.addObject("trainNumber", trainNumber);
            model.addObject("stationA", stationAId);
            model.addObject("stationB", stationBId);
            model.addObject(MESSAGE, "you have already bought a ticket on train " + trainNumber);
            return model;
        }
        TicketDto ticketDto = ticketService.addTicket(trainNumber, stationAId, stationBId, passenger);
        model.addObject("ticket", ticketDto);
        model.setViewName("ticket.jsp");
        return model;
    }

    @ResponseBody
    @GetMapping(value = "/getPassengers")
    public ModelAndView getPassengers(@RequestParam("trainNumber") int trainNumber,
                                     ModelAndView model) {
        List<PassengerDto> passengersDto = passengerService.getPassengers(trainNumber);
        if (passengersDto != null && !passengersDto.isEmpty()) {
            model.addObject("passengers", passengersDto);
            model.addObject("train", trainNumber);
            model.setViewName("passenger_list.jsp");
            return model;
        } else {
            model.addObject(MESSAGE, "no passengers on train " + trainNumber);
            model.addObject("listOfTrains", trainService.getAllTrains());
            model.setViewName("trainsList.jsp");
            return model;
        }
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ModelAndView handleException(Exception e) {
        log.error(e.getCause());
        return new ModelAndView("passenger.jsp").addObject(MESSAGE, "incorrect date");
    }
}
