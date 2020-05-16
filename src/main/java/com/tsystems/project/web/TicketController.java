package com.tsystems.project.web;

import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.TrainTicketValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;

/**
 * author Vitalii Nefedov
 */

@Controller
public class TicketController {

    private final TrainTicketValidator trainTicketValidator;

    private final TrainService trainService;

    @Autowired
    public TicketController(TrainTicketValidator trainTicketValidator, TrainService trainService) {
        this.trainTicketValidator = trainTicketValidator;
        this.trainService = trainService;
    }

    /**
     * @param trainDto     trainDto
     * @param result       result of validation
     * @param modelAndView modelAndView
     * @return modelAndView
     */
    @ResponseBody
    @GetMapping(value = "/train-verification")
    public ModelAndView verifyTrain(@Valid @ModelAttribute("trainDto") TrainDto trainDto,
                                    BindingResult result, ModelAndView modelAndView) {
        trainTicketValidator.validate(trainDto, result);
        if (result.hasErrors()) {
            trainDto.setDepartureTime(trainDto.getAllTrainsDepartureTime());
            trainDto.setArrivalTime(trainDto.getAllTrainsArrivalTime());
            List<TrainDto> trains = trainService.getTrainListBetweenTwoPoints(trainDto);
            modelAndView.addObject("trains", trains);
            modelAndView.addObject("train", trains.get(0));
            modelAndView.setViewName("trips.jsp");
            modelAndView.addObject(result.getModel());
            return modelAndView;
        }
        modelAndView.addObject("train", trainDto);
        modelAndView.setViewName("passenger.jsp");
        return modelAndView;
    }
}
