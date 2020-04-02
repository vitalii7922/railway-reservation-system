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

    @GetMapping(value = "/logout")
    public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login.jsp");
        HttpSession session = request.getSession();
        session.setAttribute("admin", null);
        return mav;
    }

  /*  // If user will be successfully authenticated he/she will be taken to the login secure page.
    @GetMapping(value="/admin")
    public ModelAndView adminPage() {

        ModelAndView m = new ModelAndView();
        m.setViewName("admin");

        return m;
    }*/

    // Spring security will see this message.
    /*@PostMapping(value = "/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) {

        ModelAndView m = new ModelAndView();
        if (error != null) {
            m.addObject("error", "Invalid username and password error.");
        }

        if (logout != null) {
            m.addObject("msg", "You have left successfully.");
        }

        m.setViewName("menu.jsp");
        return m;
    }

    @GetMapping(value = "/logout")
    public ModelAndView logOut(ModelAndView modelAndView) {
        modelAndView.setViewName("login.jsp");
        return modelAndView;
    }*/
}

