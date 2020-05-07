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
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * author Vitalii Nefedov
 */

@Controller
public class PassengerController {

    private final TicketService ticketService;

    private final PassengerService passengerService;

    private final TrainService trainService;

    private final PassengerValidator passengerValidator;

    @Autowired
    public PassengerController(TicketService ticketService, PassengerService passengerService, TrainService trainService,
                               PassengerValidator passengerValidator) {
        this.ticketService = ticketService;
        this.passengerService = passengerService;
        this.trainService = trainService;
        this.passengerValidator = passengerValidator;
    }


    /**
     * @param passengerTrainDto passenger and train data
     * @param bindingResult     result of validation
     * @param modelAndView      model and view
     * @return modelAndView
     */
    @ResponseBody
    @PostMapping(value = "/ticket")
    public ModelAndView addPassengerTicket(@Valid @ModelAttribute("passengerTrainDto") PassengerTrainDto passengerTrainDto,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        modelAndView.setViewName("passenger.jsp");
        passengerValidator.validate(passengerTrainDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return modelAndView.addObject(bindingResult.getModel());
        }
        PassengerDto passenger = passengerService.getPassenger(passengerTrainDto);
        if (passenger == null) {
            passenger = passengerService.addPassenger(passengerTrainDto);
        }
        TicketDto ticketDto = ticketService.addTicket(passengerTrainDto, passenger);
        modelAndView.addObject("ticket", ticketDto);
        modelAndView.setViewName("ticket.jsp");
        return modelAndView;
    }

    /**
     * @param trainNumber  train number
     * @param modelAndView modelAndView
     * @return model and view
     */
    @ResponseBody
    @GetMapping(value = "/employee/passengers")
    public ModelAndView getPassengers(@RequestParam("trainNumber") int trainNumber,
                                      ModelAndView modelAndView) {
        List<PassengerDto> passengersDto = passengerService.getPassengers(trainNumber);
        if (!CollectionUtils.isEmpty(passengersDto)) {
            modelAndView.addObject("passengers", passengersDto);
            modelAndView.addObject("train", trainNumber);
            modelAndView.setViewName("passenger_list.jsp");
            return modelAndView;
        } else {
            modelAndView.addObject("message", "no passengers on train " + trainNumber);
            modelAndView.addObject("listOfTrains", trainService.getTrainList());
            modelAndView.setViewName("trainsList.jsp");
            return modelAndView;
        }
    }
}
