package com.tsystems.project.dao;

import com.tsystems.project.domain.Station;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StationDao extends AbstractDao<Station> {

    public StationDao() {
        super(Station.class);
    }

    public Station findByName(String name) {
        getCurrentSession().beginTransaction();
        String queryString = "SELECT s FROM Station s WHERE LOWER(s.name) = :name";
        Query query = getCurrentSession().createQuery(queryString);
        query.setParameter("name", name.toLowerCase());
        List<Station> stations = query.getResultList();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        if (stations.isEmpty()) {
            return null;

        } else {
            return stations.get(0);
        }

    }
}
