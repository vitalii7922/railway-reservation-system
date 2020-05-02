package com.tsystems.project.web;
import com.tsystems.project.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {

    @Autowired
    AdminService adminService;

    @ResponseBody
    @GetMapping(value = "/login")
    public ModelAndView loginAdminProcess(@RequestParam(value = "error", required = false) String error,
                                          @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject("message", "Invalid username or password!");
        }
        if (logout != null) {
            modelAndView.addObject("message", "You've been logged out successfully");
        }
        modelAndView.setViewName("login.jsp");
        return modelAndView;
    }

    @ResponseBody
    @GetMapping(value = "/403")
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.addObject("message", "You don't have the access");
        model.setViewName("login.jsp");
        return model;
    }
}

