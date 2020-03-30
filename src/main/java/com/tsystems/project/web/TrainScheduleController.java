package com.tsystems.project.web;
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
        List<TrainDto> trains = null;

        if (stationFrom != null && stationTo != null) {
            trains = trainService.getTrainsByStations(stationFrom, stationTo, timeDeparture, timeArrival);
        }

        if (trains != null && !trains.isEmpty()) {
            model.setViewName("trips.jsp");
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
        model.addObject("listOfStations", trains);

        LocalDateTime time = schedule.getArrivalTime();

        if (time.isAfter(timeDeparture) || timeDeparture.isAfter(timeArrival)) {
            return model;
        }



        Station from = stationService.getStationById(train.getDestinationStation().getId());
        Station to = stationService.getStationByName(destinationStation);

        if (from == null || to == null) {
          return model;
        }

        if (from.getId() == to.getId()) {
            return model;
        }

        if (numberOfSeats == null || numberOfSeats.equals("\\s*")) {
            model.addObject("message", "you haven't number of seats");
            return model;
        }

        train = trainService.addTrain(number, from, to, numberOfSeats);

        if (train != null) {
            scheduleService.addSchedule(train, LocalDateTime.parse(departureTime), LocalDateTime.parse(arrivalTime));
        }

        trains = trainService.getAllTrainsByNumbers(number);

        if (trains.stream().anyMatch(t -> t.getDestinationStation().equals(to) || t.getOriginStation().equals(from))) {
            return model;
        }

        model.addObject("listOfStations", trains);

        return model;
    }
}
