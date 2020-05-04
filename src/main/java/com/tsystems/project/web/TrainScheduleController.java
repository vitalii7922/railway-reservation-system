package com.tsystems.project.web;

import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.TrainService;
import com.tsystems.project.validator.TripsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

@Controller
public class TrainScheduleController {

    @Autowired
    TrainService trainService;

    @Autowired
    TripsValidator tripsValidator;

    @ResponseBody
    @GetMapping(value = "/trips")
    public ModelAndView addTrain(@RequestParam("from") String stationNameA,
                                 @RequestParam("to") String stationNameB,
                                 @RequestParam("time_departure") String timeDeparture,
                                 @RequestParam("time_arrival") String timeArrival, ModelAndView model) {
        List<TrainDto> trains = trainService.getTrainListBetweenTwoPoints(trainService.initializeTrainDto(stationNameA,
                stationNameB, timeDeparture, timeArrival));
        if (!CollectionUtils.isEmpty(trains)) {
            model.setViewName("trips.jsp");
            model.addObject("timeDeparture", timeDeparture);
            model.addObject("timeArrival", timeArrival);
            model.addObject("train", trains.get(0));
            model.addObject("trains", trains);
            return model;
        } else {
            model.setViewName("index.jsp");
            model.addObject("message", "No trains");
            return model;
        }
    }

    @ResponseBody
    @PostMapping(value = "/admin/trips")
    public ModelAndView addTrips(@ModelAttribute("trainDto") TrainDto trainDto,
                                 BindingResult bindingResult,
                                 ModelAndView modelAndView)  {
        modelAndView.setViewName("trains.jsp");
        TrainDto train = trainService.getTrainByNumber(trainDto.getNumber());
        modelAndView.addObject("train", trainDto.getNumber());
        tripsValidator.validate(trainDto, bindingResult);
        List<TrainStationDto> trains;
        if (bindingResult.hasErrors()) {
            trains = trainService.getTrainRoutByTrainNumber(trainDto.getNumber());
            modelAndView.addObject("trainList", trains);
            modelAndView.addObject(bindingResult.getModel());
            return modelAndView;
        }
        trainDto.setOriginStation(train.getDestinationStation());
        trainService.addTrain(trainDto);
        trains = trainService.getTrainRoutByTrainNumber(trainDto.getNumber());
        modelAndView.addObject("trainList", trains);
        return modelAndView;
    }
}
