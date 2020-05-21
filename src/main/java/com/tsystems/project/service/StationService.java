package com.tsystems.project.service;

import com.tsystems.project.converter.StationConverter;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dto.StationDto;
import com.tsystems.project.model.Station;
import com.tsystems.project.sender.StationSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author Vitalii Nefedov
 */

@Service
public class StationService {

    private final StationDao stationDao;

    private final StationConverter stationConverter;

    private final StationSender sender;

    public StationService(StationDao stationDao, StationConverter stationConverter, StationSender sender) {
        this.stationDao = stationDao;
        this.stationConverter = stationConverter;
        this.sender = sender;
    }

    /**
     * @param name station name
     * @return stationDto
     */
    @Transactional
    public StationDto addStation(String name) {
        Station station = new Station();
        if (!name.matches("\\s*") && stationDao.findByName(name) == null) {
            station.setName(name.toUpperCase());
            station = stationDao.create(station);
            sender.init();
            return stationConverter.convertToStationDto(station);
        }
        return null;
    }

    /**
     * @param id station id
     * @return station model
     */
    public Station getStationById(long id) {
        return stationDao.findOne(id);
    }

    /**
     * @param stationName station name
     * @return station
     */
    public Station getStationByName(String stationName) {
        return stationDao.findByName(stationName);
    }

    /**
     * @return list of stationDto
     */
    public List<StationDto> getAllStations() {
        List<Station> stations = stationDao.findAll();
        return stations.stream().map(stationConverter::convertToStationDto).sorted().collect(Collectors.toList());
    }
}
