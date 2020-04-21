package com.tsystems.project.web;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class ScheduleController {

    @Autowired
    StationService stationService;

    @Autowired
    ScheduleService scheduleService;

    @ResponseBody

    @GetMapping(value = "/schedules")
    public ModelAndView getSchedule(@RequestParam("stationId") long stationId, ModelAndView model) {
        List<ScheduleDto> schedules = scheduleService.getSchedulesByStationId(stationId);
        if (schedules != null && !schedules.isEmpty()) {
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

    @ResponseBody
    @GetMapping(value = "/schedules/{stationId}")
    public JsonArray getSchedule(@PathVariable("stationId") long stationId) {
        List<ScheduleDto> scheduleDtos = scheduleService.getSchedulesByStationId(stationId);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(scheduleDtos, new TypeToken<List<ScheduleDto>>() {}.getType());
        return element.getAsJsonArray();
    }
}
