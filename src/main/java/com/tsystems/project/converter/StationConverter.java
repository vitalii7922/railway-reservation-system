package com.tsystems.project.converter;

import com.tsystems.project.dto.StationDto;
import com.tsystems.project.model.Station;
import org.springframework.stereotype.Component;

@Component
public class StationConverter {

    public StationDto convertToStationDto(Station station) {
        StationDto stationDto = new StationDto();
        if (station != null) {
            stationDto.setId(station.getId());
            stationDto.setName(station.getName());
        }
        return stationDto;
    }
}
