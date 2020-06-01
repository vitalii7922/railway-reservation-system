package com.tsystems.project.converter;

import com.tsystems.project.domain.Schedule;
import com.tsystems.project.dto.ScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author Vitalii Nefedov
 */
@Component
public class ScheduleMapper {

    private final TimeConverter timeConverter;

    @Autowired
    public ScheduleMapper(TimeConverter timeConverter) {
        this.timeConverter = timeConverter;
    }

    /**
     * convert schedule to scheduleDto
     *
     * @param schedule schedule
     * @return scheduleDto
     */
    public ScheduleDto convertToScheduleDto(Schedule schedule) {
        ScheduleDto scheduleDto = null;
        if (schedule != null) {
            scheduleDto = ScheduleDto.builder()
                    .id(schedule.getId())
                    .trainNumber(schedule.getTrain().getNumber())
                    .trainId(schedule.getTrain().getId())
                    .stationName(schedule.getStation().getName())
                    .build();
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
