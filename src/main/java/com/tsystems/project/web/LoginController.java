package com.tsystems.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Controller
public class LoginController {

    @GetMapping(value = "/login")
    public String enterToMenu(@RequestParam("uname") String login, @RequestParam("psw") String password) {
        if (Objects.equals(login, "admin") && Objects.equals(password, "000")) {
            return "menu.jsp";
        }else {
            return "message_login.jsp";
        }

    }
}
