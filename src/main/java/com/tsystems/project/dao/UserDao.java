package com.tsystems.project.dao;

import com.tsystems.project.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super(User.class);
    }
}
