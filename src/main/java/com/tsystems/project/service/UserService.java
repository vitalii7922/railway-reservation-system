package com.tsystems.project.service;

import com.tsystems.project.dao.UserDao;
import com.tsystems.project.domain.Ticket;
import com.tsystems.project.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserDao userDao;

    @Transactional
    public User addUser(User user) {
        userDao.create(user);
        return user;
    }

    @Transactional
    public User editUser(User user) throws RuntimeException {
        userDao.update(user);
        return user;
    }

    @Transactional
    public void removeUser(User user) {
        userDao.delete(user);
    }

    public List<Ticket> getAllUsers() {
        return userDao.findAll();
    }
}
