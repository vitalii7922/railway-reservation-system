package com.tsystems.project.web;

import com.tsystems.project.dto.StationDto;
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

    private String messageStation = "messageStation";

    @ResponseBody
    @PostMapping(value = "/admin/station")
    public ModelAndView addStation(@RequestParam("station") String stationName, Model model) {
        StationDto stationDto = stationService.addStation(stationName);
        if (stationDto != null) {
            model.addAttribute(messageStation, "Station " + stationDto.getName() + " has been added");
        } else {
            model.addAttribute(messageStation, "Station " + stationName + " exists or you entered empty line");
        }
        return new ModelAndView("menu.jsp");
    }

    @GetMapping(value = "/stations-all")
    public ModelAndView getStations(Model model) {
        List<StationDto> stations = stationService.getAllStations();
        if (stations != null && !stations.isEmpty()) {
            model.addAttribute("name", stations.get(0));
            model.addAttribute("listOfParams", stations);
            return new ModelAndView("station.jsp");
        } else {
            model.addAttribute(messageStation, "no stations");
            return new ModelAndView("menu.jsp");
        }
    }
}
