package com.tsystems.project.service;

import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.dao.StationDao;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.dto.TrainDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    TrainDao trainDao;

    @Autowired
    StationDao stationDao;

    @Autowired
    ModelMapper modelMapper;

    private static final Log log = LogFactory.getLog(PassengerService.class);

    @Transactional
    public void addSchedule(TrainDto trainDto, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        Train train = null;
        train = modelMapper.map(trainDto, Train.class);
        Schedule scheduleDeparture = null;
        Schedule scheduleArrival = null;

        try {
            scheduleDeparture = new Schedule();
            scheduleDeparture.setTrain(train);
            scheduleDeparture.setDepartureTime(departureTime);
            scheduleDeparture.setStation(train.getOriginStation());
            scheduleDao.create(scheduleDeparture);

            scheduleArrival = new Schedule();
            scheduleArrival.setTrain(train);
            scheduleArrival.setArrivalTime(arrivalTime);
            scheduleArrival.setStation(train.getDestinationStation());
            scheduleDao.create(scheduleArrival);
        } catch (Exception e) {
            log.error(e.getCause());
        }
    }

    public Schedule getScheduleByTrainId(long id) {
        return scheduleDao.findByTrainId(id);
    }


    public List<ScheduleDto> getSchedulesByStationId(long id) {
        List<Schedule> schedules = scheduleDao.findByStationId(id);
        Type listType = null;
        List<ScheduleDto> scheduleDtos = null;

        if (schedules != null) {
        listType = new TypeToken<List<ScheduleDto>>() {}.getType();
        scheduleDtos = new ModelMapper().map(schedules, listType);
        List<Long> trainsId = new ArrayList<>();

        for (int i = 0; i < scheduleDtos.size(); i++) {
            for (int j = i + 1; j < scheduleDtos.size(); j++) {
                if (scheduleDtos.get(i).getTrain().getNumber() == scheduleDtos.get(j).getTrain().getNumber()) {
                    scheduleDtos.get(i).setDepartureTime(scheduleDtos.get(j).getDepartureTime());
                    trainsId.add(scheduleDtos.get(j).getTrain().getId());
                }
            }
        }

        Iterator<ScheduleDto> iterator = scheduleDtos.iterator();

        while (iterator.hasNext()) {
            long trainId = iterator.next().getTrain().getId();
            if (trainsId.contains(trainId)){
                   iterator.remove();
            }
        }
        }
        return scheduleDtos;
    }
}

