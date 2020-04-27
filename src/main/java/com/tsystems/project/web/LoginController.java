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
    @PostMapping(value = "/login")
    public ModelAndView loginAdminProcess(/*@RequestParam(value = "error", required = false) String error,
                                          @RequestParam(value = "logout", required = false) String logout*/
            @ModelAttribute("login") String login,
            @ModelAttribute("password") String password) {
       /* ModelAndView mav;
        Admin admin = adminService.validate(login, password);
        if (admin != null) {
            mav = new ModelAndView("menu.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
        } else {
            mav = new ModelAndView("login.jsp");
            mav.addObject("message", "login or password is wrong");
        }*/
      /*  ModelAndView modelAndView = new ModelAndView();
        if (error != null) {
            modelAndView.addObject("message", "Invalid username and password!");
        }
        if (logout != null) {
            modelAndView.addObject("message", "You've been logged out successfully.");
        }*/
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu.jsp");
        return modelAndView;
    }

/*    @ResponseBody
    @PostMapping(value = "/login")
    public ModelAndView loginAdminProcess(@RequestParam("username") String username, @RequestParam("password") String password) {
        *//*ModelAndView mav;
        Admin admin = adminService.validate(login, password);
        if (admin != null) {
            mav = new ModelAndView("menu.jsp");
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
        } else {
            mav = new ModelAndView("login.jsp");
            mav.addObject("message", "login or password is wrong");
        }*//*
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("menu.jsp");
        return modelAndView;
    }*/

/*    @GetMapping(value = "/logout")
    public ModelAndView logOut(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login.jsp");
        HttpSession session = request.getSession();
        session.setAttribute("admin", null);
        return mav;
    }

    @RequestMapping(value = "/403", method = RequestMethod.POST)
    public ModelAndView accesssDenied() {

        ModelAndView model = new ModelAndView();

        //check if user is login
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            UserDetails userDetail = (UserDetails) auth.getPrincipal();
//            model.addObject("message", userDetail.getUsername());
        }

        model.setViewName("login.jsp");
        return model;
    }*/
}

