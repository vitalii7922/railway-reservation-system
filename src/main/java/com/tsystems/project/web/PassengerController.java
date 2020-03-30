package com.tsystems.project.web;

import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.PassengerService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.TicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @ResponseBody
    @GetMapping(value = "/addPassengerTicket")
    public ModelAndView addPassenger(@RequestParam("trainNumber") int trainNumber,
                                     @RequestParam("stationA") long stationAId,
                                     @RequestParam("stationB") long stationBId,
                                     @RequestParam("first_name") String firstName,
                                     @RequestParam("last_name") String lastName,
                                     @RequestParam("date_of_birth") String birthDate,
                                     ModelAndView model) {

        PassengerDto passenger = null;
        model.setViewName("passenger.jsp");

        if (firstName != null && lastName != null && birthDate != null) {
            passenger = passengerService.getPassenger(firstName, lastName, birthDate);
        } else {
            model.addObject("message", "you added uncorrected data");
            return model;
        }

        if (passenger == null) {
            passenger = passengerService.addPassenger(firstName, lastName, birthDate);
        }

        if (!ticketValidator.verifyPassenger(trainNumber, passenger)) {
            model.addObject("message", "you have already bought a ticket for this train");
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
            List<TrainDto> trains = trainService.getAllTrains();
            model.addObject("listOfTrains",trains);
            model.setViewName("trainsList.jsp");
            return model;
        }
    }
}
