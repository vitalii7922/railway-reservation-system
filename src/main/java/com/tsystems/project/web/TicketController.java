package com.tsystems.project.web;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
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
import java.util.List;

@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketValidator ticketValidator;

    @Autowired
    TrainService trainService;

    @Autowired
    StationService stationService;


    @ResponseBody
    @GetMapping(value = "/addTicket")
    public ModelAndView addTicket(@RequestParam("trainNumber") int trainNumber,
                                  @RequestParam("stationA") long originStationId,
                                  @RequestParam("stationB") long destinationStationId,
                                  @RequestParam("departureTime") String departureTime,
                                  @RequestParam("arrivalTime") String arrivalTime,
                                  ModelAndView model, HttpServletResponse response, HttpServletRequest request) {

        if (!ticketValidator.verifyTime(departureTime)) {
            model.setViewName("trips.jsp");
            List<TrainDto> trainsDto = trainService.getTrainsByStations(stationService.getStationById(originStationId), stationService.getStationById(destinationStationId), departureTime, arrivalTime);
            model.addObject("trains", trainsDto);
            model.addObject("train", trainsDto.get(0));
            model.addObject("message", "you cannot buy a ticket 10 minutes before the train " + trainNumber + " departures");
            return model;
        }

        if (!ticketValidator.verifySeats(trainNumber, originStationId, destinationStationId)) {
            model.setViewName("trips.jsp");
            List<TrainDto> trainsDto = trainService.getTrainsByStations(stationService.getStationById(originStationId), stationService.getStationById(destinationStationId), departureTime, arrivalTime);
            model.addObject("trains", trainsDto);
            model.addObject("train", trainsDto.get(0));
            model.addObject("message", "no free seats on train " + trainNumber);
            return model;
        }

        model.addObject("trainNumber", trainNumber);
        model.addObject("stationA", originStationId);
        model.addObject("stationB", destinationStationId);
        model.setViewName("passenger.jsp");
        return model;
    }
}
