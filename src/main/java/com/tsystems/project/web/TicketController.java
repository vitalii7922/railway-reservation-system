package com.tsystems.project.web;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    TrainService trainService;

    @Autowired
    StationService stationService;

    @Autowired
    TimeConverter timeConverter;


    @ResponseBody
    @GetMapping(value = "/addTicket")
    public ModelAndView addTicket(@RequestParam("trainNumber") int trainNumber,
                                  @RequestParam("stationA") long originStationId,
                                  @RequestParam("stationB") long destinationStationId,
                                  @RequestParam("departureTime") String departureTime,
                                  @RequestParam("arrivalTime") String arrivalTime,
                                  @RequestParam("timeDeparture") String departure,
                                  @RequestParam("timeArrival") String arrival, ModelAndView model) {


        if (!ticketService.verifyTime(timeConverter.reversedConvertDateTime(departureTime).toString())) {
            model.setViewName("trips.jsp");
            List<TrainDto> trainsDto = trainService.getTrainsByStations(stationService.getStationById(originStationId),
                    stationService.getStationById(destinationStationId), departure, arrival);
            model.addObject("trains", trainsDto);
            model.addObject("train", trainsDto.get(0));
            model.addObject("timeDeparture", departure);
            model.addObject("timeArrival", arrival);
            model.addObject("message", "you cannot buy a ticket 10 minutes before the train " + trainNumber + " departures");
            return model;
        }

        if (!ticketService.verifySeats(trainNumber, originStationId, destinationStationId)) {
            model.setViewName("trips.jsp");
            List<TrainDto> trainsDto = trainService.getTrainsByStations(stationService.getStationById(originStationId),
                    stationService.getStationById(destinationStationId), departure, arrival);
            model.addObject("trains", trainsDto);
            model.addObject("train", trainsDto.get(0));
            model.addObject("timeDeparture", departure);
            model.addObject("timeArrival", arrival);
            model.addObject("message", "no free seats on train " + trainNumber);
            return model;
        }

        model.addObject("trainNumber", trainNumber);
        model.addObject("stationA", originStationId);
        model.addObject("stationB", destinationStationId);
        model.addObject("departureTime", departureTime);
        model.setViewName("passenger.jsp");
        return model;
    }
}
