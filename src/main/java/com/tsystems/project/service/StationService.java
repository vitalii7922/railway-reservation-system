package com.tsystems.project.service;

import com.tsystems.project.converter.StationConverter;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dto.StationDto;
import com.tsystems.project.model.Station;
import com.tsystems.project.sender.StationSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    @Autowired
    StationDao stationDao;

    @Autowired
    StationConverter stationConverter;

    @Autowired
    StationSender sender;

    private static final Log log = LogFactory.getLog(StationService.class);

    @Transactional
    public StationDto addStation(String name) {
        StationDto stationDto = null;
        Station station = new Station();
        if (!name.matches("\\s*") && stationDao.findByName(name) == null) {
            station.setName(name.toUpperCase());
            log.info("--------Station has been added-------------");
            sender.send();
            stationDto = stationConverter.convertToStationDto(stationDao.create(station));
            return stationDto;
        }
        return stationDto;
    }

    public Station getStationById(long id) {
        return stationDao.findOne(id);
    }

    public Station getStationByName(String stationName) {
        return stationDao.findByName(stationName);
    }

    public List<StationDto> getAllStations() {
        List<Station> stations = stationDao.findAll();
        return stations.stream().map(s -> stationConverter
                .convertToStationDto(s)).sorted().collect(Collectors.toList());
    }
}
