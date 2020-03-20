package com.tsystems.project.web;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TrainController {

    @Autowired
    StationService stationService;

    @Autowired
    TrainService trainService;

    @Autowired
    ScheduleService scheduleService;

    @RequestMapping(value = "add_train", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelAndView addTrain(@RequestParam("train_number") String trainNumber,
                                 @RequestParam("origin_station") String originStation,
                                 @RequestParam("destination_station") String destinationStation,
                                 @RequestParam("number_of_seats") String numberOfSeats,
                                 @RequestParam("arrival_time")String arrivalTime,
                                 @RequestParam("departure_time")String departureTime, Model model){

        Station from = stationService.getStation(originStation);
        Station to = stationService.getStation(destinationStation);
        Train train = null;
        Schedule schedule = null;

        if (originStation != null && destinationStation != null) {
            train = trainService.addTrain(trainNumber, from, to, numberOfSeats);
        }

        if (train != null) {
            schedule = scheduleService.addSchedule(train, LocalDateTime.parse(arrivalTime), LocalDateTime.parse(departureTime));
        }

        if (train != null && schedule != null) {
            model.addAttribute("messageTrain", "Train number " + train.getNumber() + " has been added");
        } else {
            model.addAttribute("messageTrain", "Train number " + trainNumber + " hasn't been added");
        }
        return new ModelAndView("menu.jsp");
    }

    @RequestMapping(value = "/get_trains", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelAndView getStations(Model model){
        List<Station> stations = stationService.getAllStations();
        model.addAttribute("listOfParams", stations);
        return new ModelAndView("menu.jsp");
    }
}
