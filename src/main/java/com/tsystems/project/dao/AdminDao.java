package com.tsystems.project.dao;

import com.tsystems.project.domain.Admin;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class AdminDao extends AbstractDao<Admin> {
    public AdminDao() {
        super(Admin.class);
    }

    public Admin find(String login, String password) {
        String queryString = "SELECT a FROM Admin a where a.login = :login and a.password = :password";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("login", login);
        query.setParameter("password", password);
        List<Admin> admins = query.getResultList();
        if (admins.isEmpty()) {
            return null;
        }
        return admins.get(0);
    }
}
