package com.tsystems.project.service;

import com.tsystems.project.converter.TrainConverter;
import com.tsystems.project.dao.TrainDao;
import com.tsystems.project.model.Train;
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

    private final TrainConverter trainConverter;

    public SeatsService(TrainDao trainDao, TrainConverter trainConverter) {
        this.trainDao = trainDao;
        this.trainConverter = trainConverter;
    }

    /**
     * @param number train number
     * @return trainDto
     */
    @Transactional
    public List<TrainDto> getTrainByNumber(int number) {
        List<Train> trains = trainDao.findAllByNumber(number);
        return trains.stream().map(trainConverter::convertToTrainDto).collect(Collectors.toList());
    }
}
