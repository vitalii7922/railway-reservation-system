package com.tsystems.project.converter;

import com.tsystems.project.model.Schedule;
import com.tsystems.project.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScheduleConverter {

    @Autowired
    TimeConverter timeConverter;

    public ScheduleDto convertToScheduleDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        if (schedule != null) {
            scheduleDto.setId(schedule.getId());
            scheduleDto.setTrainNumber(schedule.getTrain().getNumber());
            scheduleDto.setTrainId(schedule.getTrain().getId());
            scheduleDto.setStationName(schedule.getStation().getName());
            if (schedule.getArrivalTime() != null) {
                scheduleDto.setArrivalTime(timeConverter.convertDateTime(schedule.getArrivalTime()));
            }
            if (schedule.getDepartureTime() != null) {
                scheduleDto.setDepartureTime(timeConverter.convertDateTime(schedule.getDepartureTime()));
            }
        }
        return scheduleDto;
    }
}
