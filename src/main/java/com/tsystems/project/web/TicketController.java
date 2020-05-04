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

@Controller
public class TicketController {

    @Autowired
    TrainTicketValidator trainTicketValidator;

    @Autowired
    TrainService trainService;

    @ResponseBody
    @GetMapping(value = "/train-verification")
    public ModelAndView verifyTrain(@Valid @ModelAttribute("trainDto") TrainDto trainDto,
                                    BindingResult result, ModelAndView model) {
        trainTicketValidator.validate(trainDto, result);
        if (result.hasErrors()) {
            trainDto.setDepartureTime(trainDto.getAllTrainsDepartureTime());
            trainDto.setArrivalTime(trainDto.getAllTrainsArrivalTime());
            List<TrainDto> trains = trainService.getTrainListBetweenTwoPoints(trainDto);
            model.addObject("trains", trains);
            model.addObject("train", trains.get(0));
            model.setViewName("trips.jsp");
            model.addObject(result.getModel());
            return model;
        }
        model.addObject("train", trainDto);
        model.setViewName("passenger.jsp");
        return model;
    }
}
