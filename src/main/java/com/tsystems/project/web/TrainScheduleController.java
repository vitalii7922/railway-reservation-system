package com.tsystems.project.web;

import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class TrainScheduleController {

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    ScheduleService scheduleService;

    @ResponseBody
    @GetMapping(value = "/getTrips")
    public ModelAndView addTrain(@RequestParam("from") String stationNameA,
                                 @RequestParam("to") String stationNameB,
                                 @RequestParam("time_departure") String timeDeparture,
                                 @RequestParam("time_arrival") String timeArrival, ModelAndView model) {

        Station stationFrom = stationService.getStation(stationNameA);
        Station stationTo = stationService.getStation(stationNameB);

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
}
