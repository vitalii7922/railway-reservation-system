package com.tsystems.project.service;

import com.tsystems.project.converter.ScheduleConverter;
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

    private final ScheduleConverter scheduleConverter;

    private final TimeConverter timeConverter;

    private final ScheduleSender sender;

    public ScheduleService(ScheduleDao scheduleDao, ScheduleConverter scheduleConverter, TimeConverter timeConverter,
                           ScheduleSender sender) {
        this.scheduleDao = scheduleDao;
        this.scheduleConverter = scheduleConverter;
        this.timeConverter = timeConverter;
        this.sender = sender;
    }

    /**
     * @param train         train model
     * @param departureTime departure time of a train
     * @param arrivalTime   arrival time of a train
     */
    @Transactional
    public void addSchedule(Train train, LocalDateTime departureTime, LocalDateTime arrivalTime) {
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
        scheduleDao.create(scheduleDeparture);
        scheduleDao.create(scheduleArrival);
        if (departureTime.toLocalDate().equals(LocalDate.now()) || arrivalTime.toLocalDate().equals(LocalDate.now())) {
            sender.send(String.valueOf(train.getOriginStation().getId()));
            sender.send(String.valueOf(train.getDestinationStation().getId()));
        }
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
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(schedules)) {
            scheduleDtoList = schedules.stream().map(scheduleConverter::convertToScheduleDto)
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
        scheduleDtoList.removeIf(scheduleDto -> (scheduleDto.getDepartureTime() != null &&
                !timeConverter.reversedConvertDateTime(scheduleDto.getDepartureTime()).toLocalDate().equals(LocalDate.now()) &&
                scheduleDto.getArrivalTime() != null &&
                !timeConverter.reversedConvertDateTime(scheduleDto.getArrivalTime()).toLocalDate().equals(LocalDate.now())) ||
                (scheduleDto.getDepartureTime() == null && scheduleDto.getArrivalTime() != null &&
                        !timeConverter.reversedConvertDateTime(scheduleDto.getArrivalTime()).toLocalDate().equals(LocalDate.now())) ||
                (scheduleDto.getArrivalTime() == null && scheduleDto.getDepartureTime() != null &&
                        !timeConverter.reversedConvertDateTime(scheduleDto.getDepartureTime()).toLocalDate().equals(LocalDate.now())));
        return scheduleDtoList;
    }
}

