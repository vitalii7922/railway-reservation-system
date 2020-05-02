package com.tsystems.project.service;
import com.tsystems.project.converter.ScheduleConverter;
import com.tsystems.project.converter.TimeConverter;
import com.tsystems.project.dao.ScheduleDao;
import com.tsystems.project.model.Schedule;
import com.tsystems.project.model.Train;
import com.tsystems.project.dto.ScheduleDto;
import com.tsystems.project.sender.ScheduleSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.time.LocalDate;
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

    @Autowired
    TimeConverter timeConverter;

    @Autowired
    ScheduleSender sender;

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
        if (departureTime.toLocalDate().equals(LocalDate.now()) || arrivalTime.toLocalDate().equals(LocalDate.now())) {
            sender.send(String.valueOf(train.getOriginStation().getId()));
            sender.send(String.valueOf(train.getDestinationStation().getId()));
        }
    }

    public Schedule getScheduleByTrainId(long id) {
        return scheduleDao.findByTrainId(id);
    }

    public List<ScheduleDto> getSchedulesByStationId(long id) {
        List<Schedule> schedules = scheduleDao.findByStationId(id);
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(schedules)) {
            scheduleDtoList = schedules.stream().map(s -> scheduleConverter.convertToScheduleDto(s))
                    .collect(Collectors.toList());
            List<Long> trainsId = new ArrayList<>();
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

