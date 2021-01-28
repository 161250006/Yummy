package com.hungry.controller;

import com.hungry.dao.UserDao;
import com.hungry.model.Modify;
import com.hungry.model.Orders;
import com.hungry.service.MessageService;
import com.hungry.service.OrderService;
import com.hungry.service.StatisticsService;
import com.hungry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/getAllOrdersUser", method = RequestMethod.POST)
    public List<Orders> getAllOrdersUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return orderService.getAllOrdersUser((String)session.getAttribute("userName"));
    }

    @RequestMapping(value = "/getAllOrdersRestaurant", method = RequestMethod.POST)
    public List<Orders> getAllOrdersRestaurant(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return orderService.getAllOrdersRestaurant((String)session.getAttribute("restaurantID"));
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public void cancel(HttpServletRequest request,@RequestParam("orderID") int orderID) {
        orderService.cancelOrder(orderID);
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public void pay(HttpServletRequest request,@RequestParam("orderID") int orderID) {
        orderService.payOrder(orderID);
    }

    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public void unsubscribe(HttpServletRequest request,@RequestParam("orderID") int orderID,@RequestParam("unsubscribeMoney") int unsubscribeMoney) {
        orderService.unsubscribeOrder(orderID,unsubscribeMoney);
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
    public void confirm(HttpServletRequest request,@RequestParam("orderID") int orderID) {
        orderService.confirmOrder(orderID);
    }
}
