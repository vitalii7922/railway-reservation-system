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

/**
 * author Vitalii Nefedov
 */

@Controller
public class TrainScheduleController {

    private final TrainService trainService;

    private final TripsValidator tripsValidator;

    @Autowired
    public TrainScheduleController(TrainService trainService, TripsValidator tripsValidator) {
        this.trainService = trainService;
        this.tripsValidator = tripsValidator;
    }

    /**
     * @param stationNameA  origin station
     * @param stationNameB  destination station
     * @param timeDeparture departure time
     * @param timeArrival   arrival time
     * @param modelAndView  model and view
     * @return model(list of trains from point A to point B) and view
     */
    @ResponseBody
    @GetMapping(value = "/trips")
    public ModelAndView addTrain(@RequestParam("from") String stationNameA,
                                 @RequestParam("to") String stationNameB,
                                 @RequestParam("time_departure") String timeDeparture,
                                 @RequestParam("time_arrival") String timeArrival, ModelAndView modelAndView) {
        List<TrainDto> trains = trainService.getTrainListBetweenTwoPoints(trainService.initializeTrainDto(stationNameA,
                stationNameB, timeDeparture, timeArrival));
        if (!CollectionUtils.isEmpty(trains)) {
            modelAndView.setViewName("trips.jsp");
            modelAndView.addObject("timeDeparture", timeDeparture);
            modelAndView.addObject("timeArrival", timeArrival);
            modelAndView.addObject("train", trains.get(0));
            modelAndView.addObject("trains", trains);
            return modelAndView;
        } else {
            modelAndView.setViewName("index.jsp");
            modelAndView.addObject("message", "No trains");
            return modelAndView;
        }
    }


    /**
     * @param trainDto      train data
     * @param bindingResult result of validation
     * @param modelAndView  model and view
     * @return model(train rout) and view
     */
    @ResponseBody
    @PostMapping(value = "/admin/trips")
    public ModelAndView addTrips(@ModelAttribute("trainDto") TrainDto trainDto,
                                 BindingResult bindingResult,
                                 ModelAndView modelAndView) {
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
