package com.tsystems.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * author Vitalii Nefedov
 */
@Controller
public class LoginController {

    private String message = "message";

    /**
     * @param error  incorrect password or login entered
     * @param logout logout information
     * @return modelAndView(login.jsp)
     */
    @ResponseBody
    @GetMapping(value = "/login")
    public ModelAndView loginAdminProcess(@RequestParam(value = "error", required = false) String error,
                                          @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject(message, "Invalid username or password!");
        }
        if (logout != null) {
            modelAndView.addObject(message, "You've been logged out successfully");
        }
        modelAndView.setViewName("login.jsp");
        return modelAndView;
    }

    /**
     * @return model and view(login.jsp)
     */
    @ResponseBody
    @GetMapping(value = "/403")
    public ModelAndView accessDenied() {
        ModelAndView model = new ModelAndView();
        model.addObject(message, "you don't have access");
        model.setViewName("login.jsp");
        return model;

    }

}

