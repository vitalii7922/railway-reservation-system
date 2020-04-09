package com.tsystems.project.web;

import com.tsystems.project.domain.Station;
import com.tsystems.project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
class StationController {

    @Autowired
    StationService stationService;

    @ResponseBody
    @GetMapping(value = "/addStation")
    public ModelAndView addStation(@RequestParam("station") String stationName, Model model) {
        Station station = stationService.addStation(stationName);
        if (station != null) {
            model.addAttribute("messageStation", "Station " + station.getName() + " has been added");
        } else {
            model.addAttribute("messageStation", "Station " + stationName + " exists or you entered empty line");
        }
        return new ModelAndView("menu.jsp");
    }

    @ResponseBody
    @GetMapping(value = "/getStations")
    public ModelAndView getStations(Model model) {
        List<Station> stations = stationService.getAllStations();
        if (stations != null && !stations.isEmpty()) {
            model.addAttribute("name", stations.get(0));
            model.addAttribute("listOfParams", stations);
            return new ModelAndView("station.jsp");
        } else {
            model.addAttribute("messageStation", "no stations");
            return new ModelAndView("menu.jsp");
        }
    }
}
