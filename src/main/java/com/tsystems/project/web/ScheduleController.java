package com.tsystems.project.web;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    StationService stationService;

    @Autowired
    ScheduleService scheduleService;

    @ResponseBody

    @GetMapping(value = "/schedules")
    public ModelAndView getSchedule(@RequestParam("stationId") long stationId, ModelAndView model) {
        List<ScheduleDto> schedules = scheduleService.getSchedulesByStationId(stationId);
        if (CollectionUtils.isEmpty(schedules)) {
            model.setViewName("schedule.jsp");
            model.addObject("schedule", schedules.get(0));
            model.addObject("schedules", schedules);
        } else {
            model.setViewName("station.jsp");
            model.addObject("listOfParams", stationService.getAllStations());
            model.addObject("message", "no trains on station " +
                    stationService.getStationById(stationId).getName());
        }
        return model;
    }
}
