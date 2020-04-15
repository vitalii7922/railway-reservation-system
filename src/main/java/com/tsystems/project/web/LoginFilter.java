/*
package com.tsystems.project.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public abstract class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        List<String> list = List.of("addTrain", "addStation", "menu.jsp", "getTrains",
                "addTrip", "addTrips", "getPassengers", "passenger_list.jsp", "seats.jsp", "trainsList.jsp", "train.jsp,"
                        + "trains.jsp");

//        List<String> list = List.of("/admin/");

        if (session.getAttribute("admin") == null && isLoginRequired(list, request)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
            dispatcher.forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }


    private boolean isLoginRequired(List<String> list, HttpServletRequest request) {
        for (String s : list) {
            if (request.getRequestURL().toString().contains(s)) {
                return true;
            }
        }
        return false;
    }

}
*/
