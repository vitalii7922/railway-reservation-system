package com.tsystems.project.web;

import com.tsystems.project.config.ModelMap;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
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

    @ResponseBody
    @GetMapping(value = "/getTrips")
    public ModelAndView addTrain(@RequestParam("from") String stationNameA,
                                 @RequestParam("to") String stationNameB,
                                 @RequestParam("time_departure") String timeDeparture,
                                 @RequestParam("time_arrival") String timeArrival, ModelAndView model) {

        Station stationFrom = stationService.getStationByName(stationNameA);
        Station stationTo = stationService.getStationByName(stationNameB);

        Map<Train, Train> trains = null;
        Map<Schedule, Schedule> schedules = new LinkedHashMap<>();

        if (stationFrom != null && stationTo != null) {
            trains = trainService.getTrainsByStations(stationFrom, stationTo, timeDeparture, timeArrival);
        }

       if (trains != null) {
           schedules = scheduleService.getSchedulesByTrains(trains);
       }

        String trip = stationNameA + " - " + stationNameB;

        model.addObject("trip", trip);
        model.addObject("schedules", schedules);
        model.setViewName("trips.jsp");

        return model;
    }

    @ResponseBody
    @GetMapping(value = "/addTrips")
    public ModelAndView addTrain(@RequestParam("train_number") int number,
                                 @RequestParam("destination_station") String destinationStation,
                                 @RequestParam("number_of_seats") String numberOfSeats,
                                 @RequestParam("departure_time") String departureTime,
                                 @RequestParam("arrival_time") String arrivalTime, ModelAndView model) {

        TrainDto train = trainService.getTrainByNumber(number);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);
        LocalDateTime timeDeparture = LocalDateTime.parse(departureTime);

        Schedule schedule = scheduleService.getScheduleByTrainId(train.getId());
        List<TrainDto> trains = trainService.getAllTrainsByNumbers(number);

        model.setViewName("trains.jsp");
        model.addObject("train", number);

        LocalDateTime time = schedule.getArrivalTime();

        if (time.isAfter(timeDeparture) || timeDeparture.isAfter(timeArrival)) {
            model.addObject("listOfStations", trains);
            return model;
        }

        Station from = stationService.getStationById(train.getDestinationStation().getId());
        Station to = stationService.getStationByName(destinationStation);

        if (from != null && to != null) {
            train = trainService.addTrain(number, from, to, numberOfSeats);
        }

        if (train != null) {
            scheduleService.addSchedule(train, LocalDateTime.parse(departureTime), LocalDateTime.parse(arrivalTime));
        }

        trains = trainService.getAllTrainsByNumbers(number);
        model.addObject("listOfStations", trains);

        return model;
    }
}
