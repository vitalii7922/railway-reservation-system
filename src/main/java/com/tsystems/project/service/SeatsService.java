package com.tsystems.project.service;

import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatsService {

    @Autowired
    TrainDao trainDao;

    @Autowired
    TrainConverter trainConverter;

    @Transactional
    public List<TrainDto> getTrainByNumber(int number) {
        List<Train> trains = trainDao.findAllByNumber(number);
        return  trains.stream().map(t -> trainConverter.convertToTrainDto(t)).collect(Collectors.toList());
    }
}
