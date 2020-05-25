package com.tsystems.project.rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.tsystems.project.dto.StationDto;
import com.tsystems.project.domain.Station;
import com.tsystems.project.service.StationService;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author Vitalii Nefedov
 */
@RestController
public class StationRestController {

    private final StationService stationService;

    @Autowired
    public StationRestController(StationService stationService) {
        this.stationService = stationService;
    }

    /**
     * @return json array of stations
     */
    @ResponseBody
    @GetMapping(value = "/stations")
    public JsonArray getStations() {
        List<StationDto> stations = stationService.getAllStations();
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(stations, new TypeToken<List<Station>>() {
        }.getType());
        return element.getAsJsonArray();
    }
}
