package com.tsystems.project.web;
import com.tsystems.project.dto.PassengerDto;
import com.tsystems.project.dto.PassengerTrainDto;
import com.tsystems.project.dto.TicketDto;
import com.tsystems.project.service.PassengerService;
import com.tsystems.project.service.TicketService;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.PassengerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class PassengerController {

    @Autowired
    TicketService ticketService;

    @Autowired
    PassengerService passengerService;

    @Autowired
    TrainService trainService;

    @Autowired
    PassengerValidator passengerValidator;


    @ResponseBody
    @PatchMapping(value = "/ticket")
    public ModelAndView addPassengerTicket(@Valid @ModelAttribute("passengerTrainDto") PassengerTrainDto passengerTrainDto,
                                     BindingResult bindingResult, ModelAndView model) {
        model.setViewName("passenger.jsp");
        passengerValidator.validate(passengerTrainDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return model.addObject(bindingResult.getModel());
        }
        PassengerDto passenger = passengerService.getPassenger(passengerTrainDto);
        if (passenger == null) {
            passenger = passengerService.addPassenger(passengerTrainDto);
        }
        TicketDto ticketDto = ticketService.addTicket(passengerTrainDto, passenger);
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
            model.addObject("message", "no passengers on train " + trainNumber);
            model.addObject("listOfTrains", trainService.getAllTrains());
            model.setViewName("trainsList.jsp");
            return model;
        }
    }
}
