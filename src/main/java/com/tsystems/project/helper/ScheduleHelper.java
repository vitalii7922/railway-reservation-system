/*
package com.tsystems.project.helper;

import com.tsystems.project.converter.ScheduleConverter;
import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.domain.Schedule;
import com.tsystems.project.dto.ScheduleDto;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Configuration
public class ScheduleHelper {

    @Autowired
    TrainConverter trainConverter;

    @Autowired
    ScheduleConverter scheduleConverter;

    private static final Log log = LogFactory.getLog(ScheduleHelper.class);
    @Bean
    public ScheduleHelper transferService() {
        return new ScheduleHelper();
    }

    public List<ScheduleDto> getTrainDepartureAndArrivalOnStation(List<Schedule> schedules) {
        List<ScheduleDto> schedulesDto = new ArrayList<>();
        try {
            for (Schedule s : schedules) {
                schedulesDto.add(scheduleConverter.convertToScheduleDto(s));
            }
            List<Long> trainsId = new ArrayList<>();
            for (int i = 0; i < schedulesDto.size(); i++) {
                for (int j = i + 1; j < schedulesDto.size(); j++) {
                    if (schedulesDto.get(i).getTrainNumber() == schedulesDto.get(j).getTrainNumber()) {
                        schedulesDto.get(i).setDepartureTime(schedulesDto.get(j).getDepartureTime());
                        trainsId.add(schedulesDto.get(j).getTrainId());
                    }
                }
            }
            Iterator<ScheduleDto> iterator = schedulesDto.iterator();
            while (iterator.hasNext()) {
                long trainId = iterator.next().getTrainId();
                if (trainsId.contains(trainId)) {
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            log.error(e.getCause());
        }

        return schedulesDto;
    }
}
*/
