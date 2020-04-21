package com.tsystems.project.web;

import com.tsystems.project.model.Admin;
import com.tsystems.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    AdminService adminService;

    @PostMapping(value = "/login")
    public ModelAndView loginAdminProcess(HttpServletRequest request,
                                          @ModelAttribute("login") String login,
                                          @ModelAttribute("password") String password) {
        ModelAndView mav;
        Admin admin = adminService.validate(login, password);
        if (admin != null) {
            mav = new ModelAndView("menu.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
        } else {
            mav = new ModelAndView("login.jsp");
            mav.addObject("message", "login or password is wrong");
        }
        return mav;
    }

    @GetMapping(value = "/logout")
    public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login.jsp");
        HttpSession session = request.getSession();
        session.setAttribute("admin", null);
        return mav;
    }
}

