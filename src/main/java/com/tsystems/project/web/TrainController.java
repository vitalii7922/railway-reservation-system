package com.tsystems.project.web;

import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.TrainValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.validation.Valid;

@Controller
public class TrainController {
    @Autowired
    TrainService trainService;

    @Autowired
    TrainValidator trainValidator;

    @ResponseBody
    @GetMapping(value = "/addTrain")
    public ModelAndView addTrain(@RequestParam("train_number") int trainNumber, ModelAndView model) {
        TrainDto train = trainService.getTrainByNumber(trainNumber);
        model.addObject("train", trainNumber);
        if (train == null) {
            model.setViewName("train.jsp");
        } else {
            List<TrainStationDto> trains = trainService.getAllTrainsByNumber(trainNumber);
            model.setViewName("trains.jsp");
            model.addObject("trainList", trains);
        }
        return model;
    }


    @PostMapping(value = "/addTrip")
    public ModelAndView addTrain(@Valid @ModelAttribute("trainDto") TrainDto trainDto,
                                 BindingResult bindingResult, Model model) {
        model.addAttribute("train", trainDto.getNumber());
        trainValidator.validate(trainDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ModelAndView("train.jsp", bindingResult.getModel());
        }
        trainService.addTrain(trainDto);
        List<TrainStationDto> trains = trainService.getAllTrainsByNumber(trainDto.getNumber());
        model.addAttribute("trainList", trains);
        return new ModelAndView("trains.jsp");
    }

    @ResponseBody
    @GetMapping(value = "/getTrains")
    public ModelAndView getTrain(ModelAndView model) {
        List<TrainDto> trains = trainService.getAllTrains();
        model.setViewName("menu.jsp");
        if (trains != null && !trains.isEmpty()) {
            model.setViewName("trainsList.jsp");
            model.addObject("listOfTrains", trains);
        } else {
            model.addObject("messageTrain", "no trains");
        }
        return model;
    }
}
