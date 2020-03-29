package com.tsystems.project.web;

import com.tsystems.project.domain.Passenger;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;


@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;

 /*   @ResponseBody
    @GetMapping(value = "/buyTicket")
    public ModelAndView addTicketPassesnger(@RequestParam("train") int trainNumber,
                                            @RequestParam("stationA") long originStationId,
                                            @RequestParam("stationB") long destinationStationId,
                                            @RequestParam("departureTime") LocalDateTime departureTime,
                                            ModelAndView model) {
        model.addObject("train", trainNumber);
        model.addObject("stationA", originStationId);
        model.addObject("stationB", destinationStationId);
        model.addObject("departureTime", departureTime);
        model.setViewName("passenger.jsp");
        return model;
    }
*/
    @ResponseBody
    @GetMapping(value = "/addTicket")
    public ModelAndView addTicketPasesnger(@RequestParam("train") Train train,
                                           @RequestParam("passenger") Passenger passenger,
                                           ModelAndView model) {
        Ticket ticket = ticketService.addTicket(train, passenger);
        return model;
    }
}
