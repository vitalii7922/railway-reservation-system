package com.tsystems.project.web;

import com.tsystems.project.domain.Station;
import com.tsystems.project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/index")
class StationController {
    @Autowired
    StationService stationService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addStation(@RequestParam("station") String stationName){
        Station station = new Station();
        station.setName(stationName);
        stationService.addStation(station);
        return "Station's been added successfully";
    }
}
