package com.tsystems.project.web;

import com.tsystems.project.domain.Admin;
import com.tsystems.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

    @Autowired
    AdminService adminService;

    @PostMapping(value = "/login")
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
                                     @ModelAttribute("login") String login, @ModelAttribute("password") String password) {
        ModelAndView mav = null;
        Admin admin = adminService.validate(login, password);

        if (admin != null) {
            mav = new ModelAndView("menu.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
        } else {
            mav = new ModelAndView("login.jsp");
            mav.addObject("message", "login or password is wrong!!");
        }
        return mav;
    }

    @PostMapping(value = "/logout")
    public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login.jsp");
        HttpSession session = request.getSession();
        session.setAttribute("admin", null);
        return mav;
    }

}

