package com.hungry.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class JumpController {

    @RequestMapping(value = "/manager",produces = "text/plain;charset=UTF-8")
    public String manager(){
        return "manager";
    }

    @RequestMapping(value = "/managerMessage",produces = "text/plain;charset=UTF-8")
    public String managerMessage(){
        return "managerMessage";
    }

    @RequestMapping(value = "/restaurant",produces = "text/plain;charset=UTF-8")
    public String restaurant(){
        return "restaurant";
    }

    @RequestMapping(value = "/restaurantOrder",produces = "text/plain;charset=UTF-8")
    public String restaurantOrder(){
        return "restaurantOrder";
    }

    @RequestMapping(value = "/showMenu",produces = "text/plain;charset=UTF-8")
    public String showMenu(){
        return "showMenu";
    }

    @RequestMapping(value = "/user",produces = "text/plain;charset=UTF-8")
    public String user(){
        return "user";
    }

    @RequestMapping(value = "/userOrder",produces = "text/plain;charset=UTF-8")
    public String userOrder(){
        return "userOrder";
    }

    @RequestMapping(value = "/userLogout", method = RequestMethod.POST)
    public String restaurantLogout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.removeAttribute("userName");
        session.removeAttribute("name");
        return "/index";
    }

    @RequestMapping(value = "/restaurantLogout", method = RequestMethod.POST)
    public String managerLogout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.removeAttribute("restaurantName");
        session.removeAttribute("restaurantID");
        return "index";
    }

    @RequestMapping(value = "/managerLogout", method = RequestMethod.POST)
    public String userLogout(HttpServletRequest request) {
        HttpSession session=request.getSession();
        session.removeAttribute("userName");
        return "/index";
    }

}
