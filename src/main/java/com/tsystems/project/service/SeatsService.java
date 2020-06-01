package com.tsystems.project.service;

import com.tsystems.project.converter.TrainMapper;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.domain.Train;
import com.tsystems.project.dto.TrainDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author Vitalii Nefedov
 */

@Service
public class SeatsService {

    private final TrainDao trainDao;

    private final TrainMapper trainMapper;

    public SeatsService(TrainDao trainDao, TrainMapper trainMapper) {
        this.trainDao = trainDao;
        this.trainMapper = trainMapper;
    }

    /**
     * @param number train number
     * @return trainDto
     */
    @Transactional
    public List<TrainDto> getTrainByNumber(int number) {
        List<Train> trains = trainDao.findAllByNumber(number);
        return trains.stream().map(trainMapper::convertToTrainDto).collect(Collectors.toList());
    }
}
