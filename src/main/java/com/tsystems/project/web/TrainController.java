package com.tsystems.project.web;
import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
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

import java.util.List;

@Controller
public class TrainController {

    @Autowired
    StationService stationService;
    @Autowired
    TrainService trainService;

    @RequestMapping(value = "add_trains", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ModelAndView addTrain(@RequestParam("train_number") String trainNumber,
                                 @RequestParam("origin_station") String originStation,
                                 @RequestParam("destination_station") String destinationStation,
                                 @RequestParam("number_of_seats") String numberOfSeats, Model model){
        Train train = new Train();
        train.setNumber(Integer.parseInt(trainNumber));
        train.setOriginStation(stationService.getStation(originStation));
        train.setDestinationStation(stationService.getStation(destinationStation));
        train.setSeats(Integer.parseInt(numberOfSeats));
        trainService.addTrain(train);
        model.addAttribute("messageTrain", "Train number " + trainNumber + " has been added");
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
