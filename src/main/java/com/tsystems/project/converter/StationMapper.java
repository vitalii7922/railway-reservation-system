package com.tsystems.project.converter;

import com.tsystems.project.dto.StationDto;
import com.tsystems.project.domain.Station;
import org.springframework.stereotype.Component;

/**
 * author Vitalii Nefedov
 */
@Component
public class StationMapper {

    /**
     * @param station model
     * @return stationDto
     */
    public StationDto convertToStationDto(Station station) {
        StationDto stationDto = new StationDto();
        if (station != null) {
            stationDto.setId(station.getId());
            stationDto.setName(station.getName());
        }
        return stationDto;
    }
}
