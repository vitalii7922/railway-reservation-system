package com.tsystems.project.dao;

import com.tsystems.project.domain.Station;
import com.tsystems.project.domain.Train;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class StationDao extends AbstractDao<Station> {

    public StationDao() {
        super(Station.class);
    }

    public Station findByName(String name) {
        String queryString = "SELECT s FROM Station s WHERE UPPER(s.name) = :name";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name.toLowerCase());
        List<Station> stations = query.getResultList();

        if (stations.isEmpty()) {
            return null;
        } else {
            return stations.get(0);
        }
    }

    public Station findByDestinationStationId(long id) {
        String queryString = "SELECT t FROM Train t WHERE (t.destinationStation) = :id";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("id", id);
        List<Train> trains = query.getResultList();
        if (trains.isEmpty()) {
            return null;
        } else {
            return trains.get(0).getDestinationStation();
        }
    }

}
