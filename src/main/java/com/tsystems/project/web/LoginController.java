package com.tsystems.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Objects;

@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String enterToMenu(@RequestParam("uname") String login, @RequestParam("psw") String password) throws IOException {
        if (Objects.equals(login, "admin") && Objects.equals(password, "000")) {
            return "menu.jsp";
        }else {
            return "message_login.jsp";
        }

    }
}
