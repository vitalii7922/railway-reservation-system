package com.tsystems.project.web;

import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.PassengerService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class PassengerController {

    @Autowired
    TicketService ticketService;

    @Autowired
    PassengerService passengerService;

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;


    @ResponseBody
    @GetMapping(value = "/addPassenger")
    public ModelAndView addPassenger(@RequestParam("train") int trainNumber,
                                /* @RequestParam("stationA") long stationA,
                                 @RequestParam("stationB") long stationB,*/
                                 @RequestParam("departureTime") LocalDateTime departureTime,
                                 @RequestParam("first_name") String firstName,
                                 @RequestParam("last_name") String lastName,
                                 @RequestParam("date_of_birth") LocalDate birthDate,
                                 ModelAndView model) {
       /* Station originStation =  stationService.getStationById(stationA);
        Station destinationStation = stationService.getStationById(stationB);
        Ticket ticket = null;*/
       /* Passenger passenger = null;

        if (firstName != null && lastName != null && birthDate != null) {
            passenger = passengerService.getPassenger(firstName, lastName, birthDate);
        }

        if (passenger == null) {
            passenger = passengerService.addPassenger(firstName, lastName, birthDate);
        }

        model.addObject("passenger", passenger);
        model.addObject("train", trainNumber);
        model.addObject("stationA", stationA);
        model.addObject("stationB", stationB);
        model.addObject("departureTime", departureTime);
        model.setViewName("/addTicket");*/
        return model;
    }
}
