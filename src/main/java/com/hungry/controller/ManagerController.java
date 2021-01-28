package com.hungry.controller;

import com.hungry.dao.UserDao;
import com.hungry.model.Modify;
import com.hungry.model.User;
import com.hungry.service.MessageService;
import com.hungry.service.StatisticsService;
import com.hungry.service.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin2.message.Message;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Manager")
public class ManagerController {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    MessageService messageService;

    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "/getName", method = RequestMethod.POST)
    public String getName(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("userName");
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.POST)
    public List<Modify> getAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return messageService.showMessage();
    }

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public void approve(HttpServletRequest request, @RequestParam("result") String result,@RequestParam("messageID") int messageID,
                          @RequestParam("restaurantID") String restaurantID) {
        messageService.approveMessage(messageID, result, restaurantID);
    }

    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    public Map<String,Integer> getData(HttpServletRequest request) {
        return statisticsService.getStatisticsData();
    }
}
