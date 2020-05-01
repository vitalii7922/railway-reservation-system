package com.tsystems.project.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.service.ScheduleService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScheduleRestController {

    @Autowired
    ScheduleService scheduleService;

    @ResponseBody
    @GetMapping(value = "/schedules/{stationId}")
    public JsonArray getSchedules(@PathVariable("stationId") long stationId) {
        List<ScheduleDto> scheduleDtoList = scheduleService.getTodaySchedulesByStationId(stationId);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(scheduleDtoList, new TypeToken<List<ScheduleDto>>() {}.getType());
        return element.getAsJsonArray();
    }
}
