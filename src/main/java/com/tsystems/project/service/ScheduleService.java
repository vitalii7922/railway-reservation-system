package com.tsystems.project.service;

import com.tsystems.project.converter.ScheduleConverter;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.model.Schedule;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.ScheduleDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScheduleService {

    @Autowired
    ScheduleDao scheduleDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ScheduleConverter scheduleConverter;


    private Log log = LogFactory.getLog(ScheduleService.class);

    @Transactional
    public void addSchedule(Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        Schedule scheduleDeparture = new Schedule();
        Schedule scheduleArrival = new Schedule();
        scheduleDeparture.setTrain(train);
        scheduleDeparture.setDepartureTime(departureTime);
        scheduleDeparture.setStation(train.getOriginStation());
        scheduleDao.create(scheduleDeparture);
        scheduleArrival.setTrain(train);
        scheduleArrival.setArrivalTime(arrivalTime);
        scheduleArrival.setStation(train.getDestinationStation());
        scheduleDao.create(scheduleArrival);
    }

    public Schedule getScheduleByTrainId(long id) {
        return scheduleDao.findByTrainId(id);
    }

    public List<ScheduleDto> getSchedulesByStationId(long id) {
        List<Schedule> schedules = scheduleDao.findByStationId(id);
        List<ScheduleDto> scheduleDtos = new ArrayList<>();
            if (!CollectionUtils.isEmpty(schedules)) {
                scheduleDtos = schedules.stream().map(s -> scheduleConverter.convertToScheduleDto(s))
                        .collect(Collectors.toList());
                List<Long> trainsId = new ArrayList<>();
                for (int i = 0; i < scheduleDtos.size(); i++) {
                    for (int j = i + 1; j < scheduleDtos.size(); j++) {
                        if (scheduleDtos.get(i).getTrainNumber() == scheduleDtos.get(j).getTrainNumber()) {
                            scheduleDtos.get(i).setDepartureTime(scheduleDtos.get(j).getDepartureTime());
                            trainsId.add(scheduleDtos.get(j).getTrainId());
                        }
                    }
                }
                Iterator<ScheduleDto> iterator = scheduleDtos.iterator();
                while (iterator.hasNext()) {
                    long trainId = iterator.next().getTrainId();
                    if (trainsId.contains(trainId)) {
                        iterator.remove();
                    }
                }
                Collections.sort(scheduleDtos);
            }
        return scheduleDtos;
    }
}

