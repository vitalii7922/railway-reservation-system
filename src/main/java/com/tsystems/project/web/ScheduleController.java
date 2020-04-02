package com.tsystems.project.web;

import com.tsystems.project.domain.Station;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.service.ScheduleService;
import com.tsystems.project.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    StationService stationService;

    @Autowired
    ScheduleService scheduleService;

    @ResponseBody
    @GetMapping(value = "/getSchedules")
    public ModelAndView getSchedule(@RequestParam("stationId")String stationId, ModelAndView model) {
        long id = Integer.parseInt(stationId);
        List<ScheduleDto> schedules = scheduleService.getSchedulesByStationId(id);
        Station station = stationService.getStationById(id);
        model.setViewName("schedule.jsp");

        if (schedules != null) {
            model.addObject("station", station.getName());
            model.addObject("schedules", schedules);
        }
        return model;
    }
}
