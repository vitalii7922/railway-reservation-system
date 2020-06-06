package com.tsystems.project.service;

import com.tsystems.project.converter.ScheduleMapper;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.sender.ScheduleSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * author Vitalii Nefedov
 */

@Service
public class ScheduleService {

    private final ScheduleDao scheduleDao;

    private final ScheduleMapper scheduleMapper;

    private final TimeConverter timeConverter;

    private final ScheduleSender sender;

    public ScheduleService(ScheduleDao scheduleDao, ScheduleMapper scheduleMapper, TimeConverter timeConverter,
                           ScheduleSender sender) {
        this.scheduleDao = scheduleDao;
        this.scheduleMapper = scheduleMapper;
        this.timeConverter = timeConverter;
        this.sender = sender;
    }

    /**
     * @param train         train model
     * @param departureTime departure time of a train
     * @param arrivalTime   arrival time of a train
     */
    @Transactional
    public List<Schedule> addSchedule(Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {
        Schedule scheduleDeparture = Schedule.builder()
                .train(train)
                .departureTime(departureTime)
                .station(train.getOriginStation())
                .build();
        Schedule scheduleArrival = Schedule.builder()
                .train(train)
                .arrivalTime(arrivalTime)
                .station(train.getDestinationStation())
                .build();
        scheduleDeparture = scheduleDao.create(scheduleDeparture);
        scheduleArrival = scheduleDao.create(scheduleArrival);
        if (departureTime.toLocalDate().equals(LocalDate.now()) || arrivalTime.toLocalDate().equals(LocalDate.now())) {
            sender.send(String.valueOf(train.getOriginStation().getId()));
            sender.send(String.valueOf(train.getDestinationStation().getId()));
        }
        return Arrays.asList(scheduleDeparture, scheduleArrival);
    }

    /**
     * @param id identification of a schedule
     * @return schedule model
     */
    public Schedule getScheduleByTrainId(long id) {
        return scheduleDao.findByTrainId(id);
    }

    /**
     * @param id identification of a schedule
     * @return scheduleDtoList on a particular station
     */
    public List<ScheduleDto> getSchedulesByStationId(long id) {
        List<Schedule> schedules = scheduleDao.findByStationId(id);
        List<ScheduleDto> scheduleDtoList = null;
        if (!CollectionUtils.isEmpty(schedules)) {
            scheduleDtoList = schedules.stream().map(scheduleMapper::convertToScheduleDto)
                    .collect(Collectors.toList());
            Set<Long> trainsId = new HashSet<>();
            for (int i = 0; i < scheduleDtoList.size(); i++) {
                for (int j = i + 1; j < scheduleDtoList.size(); j++) {
                    if (scheduleDtoList.get(i).getTrainNumber() == scheduleDtoList.get(j).getTrainNumber()) {
                        scheduleDtoList.get(i).setDepartureTime(scheduleDtoList.get(j).getDepartureTime());
                        trainsId.add(scheduleDtoList.get(j).getTrainId());
                    }
                }
            }
            Iterator<ScheduleDto> iterator = scheduleDtoList.iterator();
            while (iterator.hasNext()) {
                long trainId = iterator.next().getTrainId();
                if (trainsId.contains(trainId)) {
                    iterator.remove();
                }
            }
            Collections.sort(scheduleDtoList);
        }
        return scheduleDtoList;
    }


    /**
     * delete elements of list of schedules that are not today's
     *
     * @param id station id
     * @return scheduleDtoList at a current day
     */
    public List<ScheduleDto> getTodaySchedulesByStationId(long id) {
        List<ScheduleDto> scheduleDtoList = getSchedulesByStationId(id);
        scheduleDtoList.removeIf(scheduleDto -> scheduleDto.getDepartureTime() != null
                && timeConverter.isToday(scheduleDto.getDepartureTime())
                && scheduleDto.getArrivalTime() != null && timeConverter.isToday(scheduleDto.getArrivalTime())
                || scheduleDto.getDepartureTime() == null && scheduleDto.getArrivalTime() != null
                && timeConverter.isToday(scheduleDto.getArrivalTime())
                || scheduleDto.getArrivalTime() == null && scheduleDto.getDepartureTime() != null
                && timeConverter.isToday(scheduleDto.getDepartureTime()));
        return scheduleDtoList;
    }
}

