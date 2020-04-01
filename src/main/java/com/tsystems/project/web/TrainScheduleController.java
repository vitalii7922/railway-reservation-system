package com.tsystems.project.web;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import com.tsystems.project.service.PassengerService;
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

import javax.persistence.criteria.CriteriaBuilder;
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

    private static final Log log = LogFactory.getLog(PassengerService.class);

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
                                 @RequestParam("number_of_seats") int numberOfSeats,
                                 @RequestParam("departure_time") String departureTime,
                                 @RequestParam("arrival_time") String arrivalTime, ModelAndView model) {

        TrainDto train = trainService.getTrainByNumber(number);
        LocalDateTime timeArrival = LocalDateTime.parse(arrivalTime);
        LocalDateTime timeDeparture = timeDeparture = LocalDateTime.parse(departureTime);

        Schedule schedule = scheduleService.getScheduleByTrainId(train.getId());
        List<TrainDto> trains;

        /*model.setViewName("trains.jsp");
        model.addObject("train", number);
        model.addObject("listOfStations", trains);*/

        LocalDateTime time = schedule.getArrivalTime();
        model.setViewName("incorrect_date_format.jsp");
        if (time.isAfter(timeDeparture) || timeDeparture.isAfter(timeArrival)) {
            model.addObject("message", "departure time is after arrival time");
            model.setViewName("incorrect_date_format.jsp");
            return model;
        }

        Station from = stationService.getStationById(train.getDestinationStation().getId());
        Station to = stationService.getStationByName(destinationStation);

        if (from == null || to == null) {
          model.addObject("message", "this station doesn't exist in DB");
          return model;
        }

        /*if (from.getId() == to.getId()) {
            model.addObject("message", "origin and destination stations are the same");
            return model;
        }*/

        trains = trainService.getAllTrainsByNumbers(number);

        if (trains.stream().anyMatch(t -> t.getDestinationStation().equals(to) || t.getOriginStation().equals(from))) {
            model.addObject("message", "destination station in a path");
            return model;
        }

        train = trainService.addTrain(number, from, to, numberOfSeats);

        if (train != null) {
            scheduleService.addSchedule(train, timeDeparture , timeArrival);
        }

        trains = trainService.getAllTrainsByNumbers(number);

        model.addObject("train", number);
        model.addObject("listOfStations", trains);
        model.setViewName("trains.jsp");

        return model;
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ModelAndView handleException(Exception e) {
        log.error(e.getCause());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", "incorrect format of date");
        return new ModelAndView("incorrect_date_format.jsp");
    }
}
