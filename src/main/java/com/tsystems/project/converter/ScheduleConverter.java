package com.tsystems.project.converter;

import com.tsystems.project.domain.Schedule;
import com.tsystems.project.dto.ScheduleDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class ScheduleConverter {

    @Autowired
    TimeConverter timeConverter;

    private static final Log log = LogFactory.getLog(ScheduleConverter.class);

    @Bean
    public ScheduleConverter transferService() {
        return new ScheduleConverter();
    }

    public ScheduleDto convertToScheduleDto(Schedule schedule) {
        ScheduleDto scheduleDto = new ScheduleDto();
        try {
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
        } catch (Exception e) {
            log.error(e.getCause());
        }
        return scheduleDto;
    }
}
