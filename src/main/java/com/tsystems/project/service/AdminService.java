package com.tsystems.project.service;

import com.tsystems.project.dao.AdminDao;
import com.tsystems.project.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    AdminDao adminDao;

    public Admin validate(String login, String password) {
        return adminDao.find(login, password);
    }
}
