package com.tsystems.project.web;

import com.tsystems.project.dto.StationDto;
import com.tsystems.project.service.StationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * author Vitalii Nefedov
 */

@Controller
class StationController {

    private final StationService stationService;

    private String messageStation = "messageStation";

    private static final Log log = LogFactory.getLog(StationController.class);

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    /**
     * @param stationName  name of station
     * @param modelAndView modelAndView
     * @return modelAndView(menu.jsp)
     */
    @ResponseBody
    @PostMapping(value = "/employee/station")
    public ModelAndView addStation(@ModelAttribute("station") String stationName, ModelAndView modelAndView) {
        StationDto stationDto = stationService.addStation(stationName);
        if (stationDto != null) {
            log.info("--------Station " + stationDto.getName() + " has been added-------------");
            modelAndView.addObject(messageStation, "Station " + stationDto.getName() + " has been added");
        } else {
            modelAndView.addObject(messageStation, "Station " + stationName + " exists in DB or you" +
                    " entered incorrect name format");
        }
        modelAndView.setViewName("menu.jsp");
        return modelAndView;
    }

    /**
     * @param model model
     * @return ModelAndView(index.jsp) or ModelAndView(station.jsp)
     */
    @ResponseBody
    @GetMapping(value = "/stations-all")
    public ModelAndView getStations(Model model) {
        List<StationDto> stations = stationService.getAllStations();
        if (stations != null && !stations.isEmpty()) {
            model.addAttribute("name", stations.get(0));
            model.addAttribute("listOfParams", stations);
            return new ModelAndView("station.jsp");
        } else {
            model.addAttribute(messageStation, "no stations");
            return new ModelAndView("index.jsp");
        }
    }
}
