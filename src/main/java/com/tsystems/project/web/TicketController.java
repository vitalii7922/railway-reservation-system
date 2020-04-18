package com.tsystems.project.web;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping(value = "/addTicket")
    public ModelAndView addTicket(@ModelAttribute("trainDto") TrainDto trainDto,
                                  BindingResult result, ModelAndView model) {
        model.addObject("train", trainDto);

        if (ticketService.verifyTime(trainDto)) {
            model.setViewName("trips.jsp");
            List<TrainDto> trainsDto = trainService.getTrainsByStations(trainDto);
            model.addObject("message", "you cannot buy a ticket 10 minutes before the train "
                    + trainDto.getNumber() + " departures");
            model.addObject("trains", trainsDto);
            return model;
        }
        if (ticketService.verifySeats(trainDto)) {
            model.setViewName("trips.jsp");
            List<TrainDto> trainsDto = trainService.getTrainsByStations(trainDto);
            model.addObject("message", "no free seats on train " + trainDto.getNumber());
            model.addObject("trains", trainsDto);
            return model;
        }
        model.setViewName("passenger.jsp");
        return model;
    }
}
