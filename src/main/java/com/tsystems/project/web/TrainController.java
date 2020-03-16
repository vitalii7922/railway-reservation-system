package com.tsystems.project.web;

import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import com.tsystems.project.service.StationService;
import com.tsystems.project.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class TrainController {
    @Autowired
    StationService stationService;
    @Autowired
    TrainService trainService;

    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelAndView addTrain(@RequestParam("train_number") String trainNumber,
                                 @RequestParam("station_departure") String stationDeparture,
                                 @RequestParam("station_arrival") String stationArrival,
                                 @RequestParam("number_of_seats") String numberOfSeats, Model model){
        Train train = new Train();
        train.setNumber(Integer.parseInt(trainNumber));
        List<Station> stations = stationService.getAllStations();
        return null;
    }
}
