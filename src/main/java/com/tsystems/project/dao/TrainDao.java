package com.tsystems.project.dao;

import com.tsystems.project.domain.Train;
import org.springframework.stereotype.Repository;

@Repository
public class TrainDao extends AbstractDao<Train> {
    public TrainDao() {
        super(Train.class);
    }
}
