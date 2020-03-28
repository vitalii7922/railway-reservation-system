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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    public ModelAndView getTrain(@RequestParam("trainNumber") String trainNumber,
                                 @RequestParam("stationA") String stationA,
                                 @RequestParam("departureTime") LocalDateTime departureTime,
                                 @RequestParam("stationB") String stationB,
                                 @RequestParam("first_name") String firstName,
                                 @RequestParam("last_name") String lastName,
                                 @RequestParam("date_of_birth") LocalDate birthDate,
                                 ModelAndView model) {
        Station stationFrom =  stationService.getStationByName(stationA);
        Station stationTo = stationService.getStationByName(stationB);
        Ticket ticket = null;
        TrainDto train = trainService.getTrainByNumber(Integer.parseInt(trainNumber));
        Passenger passenger = null;

        if (stationFrom != null && firstName != null && lastName != null && birthDate != null && train != null) {
            ticket = ticketService.getTicketByPassenger(train.getNumber(), stationFrom, firstName, lastName, birthDate);
        }

        if (ticket == null && train != null && stationFrom != null) {
            passenger = passengerService.addPassenger(train.getNumber(), stationFrom, stationTo, departureTime, firstName, lastName, birthDate);
        }

        model.addObject("passenger", passenger);
        model.addObject("train", train);
        model.setViewName("/addTicket");
        return model;
    }
}
