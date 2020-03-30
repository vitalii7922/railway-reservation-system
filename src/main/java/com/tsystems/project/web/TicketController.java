package com.tsystems.project.web;
import com.tsystems.project.validator.TicketValidator;
import com.tsystems.project.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketValidator ticketValidator;

    @ResponseBody
    @GetMapping(value = "/addTicket")
    public ModelAndView addTicket(@RequestParam("trainNumber") int trainNumber,
                                  @RequestParam("stationA") long originStationId,
                                  @RequestParam("stationB") long destinationStationId,
                                  @RequestParam("departureTime") String departureTime,
                                  ModelAndView model, HttpServletResponse response, HttpServletRequest request) {

        if (!ticketValidator.verifyTime(departureTime)) {
            model.setViewName("index.jsp");
            model.addObject("message", "you cannot buy a ticket 10 minutes before the train departures");
            return model;
        }

        if (!ticketValidator.verifySeats(trainNumber, originStationId, destinationStationId)) {
            model.setViewName("trips.jsp");
            model.addObject("message", "no free seats");
            return model;
        }

        model.addObject("trainNumber", trainNumber);
        model.addObject("stationA", originStationId);
        model.addObject("stationB", destinationStationId);
        model.setViewName("passenger.jsp");
        return model;
    }
}
