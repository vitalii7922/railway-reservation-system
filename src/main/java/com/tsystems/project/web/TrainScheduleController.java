package com.tsystems.project.web;

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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    ModelMapper mapper;

    private static final Log log = LogFactory.getLog(TrainScheduleController.class);
    private int trainNumber;
    private List<TrainStationDto> trainsList;
    String trainAttribute = "train";
    String message = "message";
    String listOfStations = "listOfStations";
    String trainsPage = "trains.jsp";

    @ResponseBody
    @GetMapping(value = "/getTrips")
    public ModelAndView addTrain(@RequestParam("from") String stationNameA,
                                 @RequestParam("to") String stationNameB,
                                 @RequestParam("time_departure") String timeDeparture,
                                 @RequestParam("time_arrival") String timeArrival, ModelAndView model) {
        Station stationFrom = stationService.getStationByName(stationNameA);
        Station stationTo = stationService.getStationByName(stationNameB);
        List<TrainDto> trains = null;

        if (stationFrom != null && stationTo != null) {
            trains = trainService.getTrainsByStations(stationFrom, stationTo, timeDeparture, timeArrival);
        }

        if (trains != null && !trains.isEmpty()) {
            model.setViewName("trips.jsp");
            model.addObject(trainAttribute, trains.get(0));
            model.addObject("trains", trains);
            model.addObject("timeDeparture", timeDeparture);
            model.addObject("timeArrival", timeArrival);
            return model;
        } else {
            model.setViewName("index.jsp");
            model.addObject(message, "No trains");
            return model;
        }
    }

    @ResponseBody
    @GetMapping(value = "/addTrips")
    public ModelAndView addTrain(@RequestParam("train_number") int number,
                                 @RequestParam("destination_station") String destinationStation,
                                 @RequestParam("number_of_seats") int numberOfSeats,
                                 @RequestParam("departure_time") String departureTime,
                                 @RequestParam("arrival_time") String arrivalTime, ModelAndView modelAndView) {

        TrainDto train = trainService.getTrainByNumber(number);
        trainNumber = number;
        Schedule schedule = scheduleService.getScheduleByTrainId(train.getId());
        List<TrainStationDto> trains = trainService.getAllTrainsByNumbers(number);
        modelAndView.addObject(trainAttribute, number);
        trainsList = trains;
        modelAndView.setViewName(trainsPage);
        LocalDateTime time = schedule.getArrivalTime();
        modelAndView.addObject(listOfStations, trains);

        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);
        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);

        if (timeDeparture.isAfter(timeArrival)) {
            modelAndView.addObject(message, "departure time is after arrival time");
            return modelAndView;
        }

        if (time.isAfter(timeDeparture)) {
            modelAndView.addObject(message, "arrival time is after departure time");
            return modelAndView;
        }

        Station from = stationService.getStationById(train.getDestinationStation().getId());
        Station to = stationService.getStationByName(destinationStation);

        if (from == null || to == null) {
            modelAndView.addObject(message, destinationStation + " doesn't exist in DB");
            return modelAndView;
        }

        if (trains.stream().anyMatch(t -> t.getStation().getId() == to.getId())) {
            modelAndView.addObject(message, "destination station in a path");
            return modelAndView;
        }

        train = trainService.addTrain(number, from, to, numberOfSeats);

        if (train != null) {
            scheduleService.addSchedule(train, timeDeparture, timeArrival);
        }

        trains = trainService.getAllTrainsByNumbers(number);

        modelAndView.addObject(listOfStations, trains);
        modelAndView.setViewName(trainsPage);

        return modelAndView;
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ModelAndView handleException(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        log.error(e.getCause());
        Map<String, ?> modelMap = Map.of(trainAttribute, trainNumber, listOfStations,
                trainsList, message, "incorrect date");
        modelAndView.addAllObjects(modelMap);
        modelAndView.setViewName(trainsPage);
        return modelAndView;
    }
}
