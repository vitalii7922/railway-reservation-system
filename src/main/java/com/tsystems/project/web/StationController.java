package com.tsystems.project.web;
import com.tsystems.project.domain.Station;
import com.tsystems.project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
class StationController {

    @Autowired
    StationService stationService;

    @ResponseBody
    @RequestMapping(value = "/add_station")
    public ModelAndView addStation(@RequestParam("station") String stationName, Model model){
        Station station = new Station();
        station.setName(stationName);
        stationService.addStation(station);
        model.addAttribute("messageStation", "Station " + stationName + " has been added");
        return new ModelAndView("menu.jsp");
    }

    @ResponseBody
    @RequestMapping(value = "/get_stations")
    public ModelAndView getStations(Model model){
        List<Station> stations = stationService.getAllStations();
        model.addAttribute("listOfParams", stations);
        return new ModelAndView("station.jsp");
    }
}
