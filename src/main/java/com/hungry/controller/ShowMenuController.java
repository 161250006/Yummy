package com.hungry.controller;

import com.hungry.dao.UserDao;
import com.hungry.model.Menu;
import com.hungry.model.Orders;
import com.hungry.model.Restaurant;
import com.hungry.model.User;
import com.hungry.service.MenuService;
import com.hungry.service.OrderService;
import com.hungry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ShowMenu")
public class ShowMenuController {

    @Autowired
    MenuService menuService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/showMenu", method = RequestMethod.POST)
    public List<Menu> register(HttpServletRequest request, @RequestParam("restaurantID") String restaurantID) {
        return menuService.getNowMenu(restaurantID);
    }


    @RequestMapping(value = "/insertOrder", method = RequestMethod.POST)
    public void getName(HttpServletRequest request ,@RequestBody Orders orders) {
        HttpSession session=request.getSession();
        orders.setUserID((Integer)session.getAttribute("userID"));
        orders.setUserName((String) session.getAttribute("userName"));
        orderService.insertOrder(orders);
    }


}
