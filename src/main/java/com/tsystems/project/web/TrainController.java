package com.tsystems.project.web;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class TrainController {

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    ScheduleService scheduleService;

    @ResponseBody
    @GetMapping(value = "/addTrain")
    public ModelAndView addTrain(@RequestParam("train_number") String trainNumber, ModelAndView model) {

        int number = Integer.parseInt(trainNumber);
        Train train = trainService.getTrainByNumber(number);
        model.addObject("train", number);
        if (train == null) {
            model.setViewName("train.jsp");
            return model;
        } else {
            List<String> stationsNames = trainService.getAllTrainsByNumbers(number);
            model.setViewName("trains.jsp");
            model.addObject("listOfStations", stationsNames);
            return model;
        }
    }

    @ResponseBody
    @GetMapping(value = "/addTrip")
    public ModelAndView addTrain(@RequestParam("train_number") String trainNumber,
                                 @RequestParam("origin_station") String originStation,
                                 @RequestParam("destination_station") String destinationStation,
                                 @RequestParam("number_of_seats") String numberOfSeats,
                                 @RequestParam("arrival_time")String departureTime,
                                 @RequestParam("departure_time")String arrivalTime, ModelAndView model){

        int number = Integer.parseInt(trainNumber);
        model.addObject("train", number);
        Station from = stationService.getStation(originStation);
        Station to = stationService.getStation(destinationStation);
        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);

        if (originStation == null || destinationStation == null) {
            model.setViewName("train.jsp");
            return model;
        }

        if (from.getId() == to.getId() || timeDeparture.isAfter(timeArrival)) {
            model.setViewName("train.jsp");
            return model;
        }

        Train train;

        train = trainService.addTrain(number, from, to, numberOfSeats);

        if (train != null) {
            scheduleService.addSchedule(train, LocalDateTime.parse(departureTime), LocalDateTime.parse(arrivalTime));
        }
        List<String> stationsNames = Arrays.asList(originStation, destinationStation);
        model.addObject("listOfStations", stationsNames);
            model.setViewName("trains.jsp");
            return model;
    }

    @ResponseBody
    @GetMapping(value = "/addTrips")
    public ModelAndView addTrain(@RequestParam("train_number") String trainNumber,
                                 @RequestParam("destination_station") String destinationStation,
                                 @RequestParam("number_of_seats") String numberOfSeats,
                                 @RequestParam("arrival_time")String departureTime,
                                 @RequestParam("departure_time")String arrivalTime, ModelAndView model){

        int number = Integer.parseInt(trainNumber);
        Train train = trainService.getTrainByNumber(number);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);
        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);

        Schedule schedule = scheduleService.getScheduleByTrainId(train.getId());
        List<String> stationsNames = trainService.getAllTrainsByNumbers(number);


        model.setViewName("trains.jsp");
        model.addObject("train", number);

        if (schedule.getArrivalTime().isAfter(timeDeparture) || timeArrival.isBefore(timeDeparture)) {
            model.addObject("listOfStations", stationsNames);
            return model;
        }

        Station from = stationService.getStationById(train.getDestinationStation().getId());
        Station to = stationService.getStation(destinationStation);

        if (from != null && to != null) {
            train = trainService.addTrain(number, from, to, numberOfSeats);
        }

        if (train != null) {
            scheduleService.addSchedule(train, LocalDateTime.parse(departureTime), LocalDateTime.parse(arrivalTime));
        }

        stationsNames = trainService.getAllTrainsByNumbers(number);
        model.addObject("listOfStations", stationsNames);

        return model;
    }

    @ResponseBody
    @GetMapping(value = "/getTrains")
    public ModelAndView getTrain(ModelAndView model) {
        List<Train> trains = trainService.getAllTrains();
        model.setViewName("trainsList.jsp");
        model.addObject("trains", trains);
        return model;
    }
}
