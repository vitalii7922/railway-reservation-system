package com.tsystems.project.web;

import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.dto.TrainStationDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
public class TrainScheduleController {

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    ModelMapper mapper;

    private static final Log log = LogFactory.getLog(TrainScheduleController.class);
    private int trainNumber;
    private List<TrainStationDto> trainStationList;
    private String trainAttribute = "train";
    private String message = "message";
    private String trainList = "trainList";
    private String trainsPage = "trains.jsp";

    @ResponseBody
    @GetMapping(value = "/getTrips")
    public ModelAndView addTrain(@RequestParam("from") String stationNameA,
                                 @RequestParam("to") String stationNameB,
                                 @RequestParam("time_departure") String timeDeparture,
                                 @RequestParam("time_arrival") String timeArrival, ModelAndView model) {

        List<TrainDto> trains = null;
        TrainDto trainDto = new TrainDto();
        trainDto.setOriginStation(stationNameA);
        trainDto.setDestinationStation(stationNameB);
        trainDto.setDepartureTime(timeDeparture);
        trainDto.setArrivalTime(timeArrival);
        trains = trainService.getTrainsByStations(trainDto);

        if (trains != null && !trains.isEmpty()) {
            model.setViewName("trips.jsp");
            model.addObject(trainAttribute, trains.get(0));
            model.addObject("trains", trains);
            model.addObject("timeDeparture", timeDeparture);
            model.addObject("timeArrival", timeArrival);
            model.addObject("originStation", stationNameA);
            model.addObject("destinationStation", stationNameB);
            return model;
        } else {
            model.setViewName("index.jsp");
            model.addObject(message, "No trains");
            return model;
        }
    }

    @ResponseBody
    @PostMapping(value = "/addTrips")
    public ModelAndView addTrain(@ModelAttribute("trainDto") TrainDto trainDto,
                                 BindingResult bindingResult,
                                 ModelAndView modelAndView) {

        TrainDto train = trainService.getTrainByNumber(trainDto.getNumber());
        trainNumber = trainDto.getNumber();
        Schedule schedule = scheduleService.getScheduleByTrainId(train.getId());
        List<TrainStationDto> trains = trainService.getAllTrainsByNumber(trainDto.getNumber());
        modelAndView.addObject(trainAttribute, trainDto.getNumber());
        trainStationList = trains;
        modelAndView.setViewName(trainsPage);
        LocalDateTime time = schedule.getArrivalTime();
        modelAndView.addObject(trainList, trains);

        LocalDateTime timeArrival = LocalDateTime.parse(trainDto.getArrivalTime());
        LocalDateTime timeDeparture = LocalDateTime.parse(trainDto.getDepartureTime());

        if (timeDeparture.isAfter(timeArrival)) {
            modelAndView.addObject(message, "departure time is after arrival time");
            return modelAndView;
        }

        if (time.isAfter(timeDeparture)) {
            modelAndView.addObject(message, "arrival time is after departure time");
            return modelAndView;
        }

        Station from = stationService.getStationByName(train.getDestinationStation());
        Station to = stationService.getStationByName(trainDto.getDestinationStation());

        if (from == null || to == null) {
            modelAndView.addObject(message, trainDto.getDestinationStation() + " doesn't exist in DB");
            return modelAndView;
        }

        if (trains.stream().anyMatch(t -> t.getStation().equalsIgnoreCase(to.getName()))) {
            modelAndView.addObject(message, "destination station in a path");
            return modelAndView;
        }

        trainDto.setOriginStation(train.getDestinationStation());
        train = trainService.addTrain(trainDto);

        if (train != null) {
            scheduleService.addSchedule(train, timeDeparture, timeArrival);
        }

        trains = trainService.getAllTrainsByNumber(trainDto.getNumber());

        modelAndView.addObject(trainList, trains);
        modelAndView.setViewName(trainsPage);

        return modelAndView;
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        log.error(e.getCause());
        Map<String, ?> modelMap = Map.of(trainAttribute, trainNumber, trainList,
                trainStationList, message, "incorrect date");
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName(trainsPage);
        return modelAndView;
    }
}
