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

/**
 * author Vitalii Nefedov
 */

@Controller
public class ScheduleController {

    private final StationService stationService;

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(StationService stationService, ScheduleService scheduleService) {
        this.stationService = stationService;
        this.scheduleService = scheduleService;
    }

    /**
     * @param stationId    station identification
     * @param modelAndView model and view
     * @return model(schedule at a station) and view()
     */
    @ResponseBody
    @GetMapping(value = "/schedules")
    public ModelAndView getSchedule(@RequestParam("stationId") long stationId, ModelAndView modelAndView) {
        List<ScheduleDto> schedules = scheduleService.getSchedulesByStationId(stationId);
        if (!CollectionUtils.isEmpty(schedules)) {
            modelAndView.setViewName("schedule.jsp");
            modelAndView.addObject("schedule", schedules.get(0));
            modelAndView.addObject("schedules", schedules);
        } else {
            modelAndView.setViewName("station.jsp");
            modelAndView.addObject("listOfParams", stationService.getAllStations());
            modelAndView.addObject("message", "no trains at station " +
                    stationService.getStationById(stationId).getName());
        }
        return modelAndView;
    }
}
