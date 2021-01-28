package com.hungry.controller;

import com.hungry.dao.UserDao;
import com.hungry.model.Menu;
import com.hungry.model.Restaurant;
import com.hungry.model.User;
import com.hungry.service.MenuService;
import com.hungry.service.MessageService;
import com.hungry.service.UserService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Restaurant")
public class RestaurantController {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    MessageService messageService;

    @Autowired
    MenuService menuService;

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Map<String,String> modify(HttpServletRequest request, @RequestParam("type") String type, @RequestParam("password") String password,
                        @RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("passwordCertain") String passwordCertain) {
        System.out.println("ifDoing");
        Map<String,String> resultMap=new HashMap<>();
        String result="";
        HttpSession session=request.getSession();
        resultMap.put("result", "success");
        if(!passwordCertain.isEmpty()) {
            if (!password.equals(passwordCertain)) {
                result = "uncertain";
                resultMap.put("result", result);
                return resultMap;
            }
        }
        String ifDoing =messageService.getExamineResult((String)session.getAttribute("restaurantID"));

        if (!ifDoing.equals("doing")) {
            System.out.println("notdoing");
            userService.modifyRestaurantImformation(name, password, type, address, (String) session.getAttribute("restaurantID"));
        }
        else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping(value = "/getName", method = RequestMethod.POST)
    public String getName(HttpServletRequest request) {
        HttpSession session=request.getSession();
        System.out.print((String)session.getAttribute("restaurantName"));
        return (String)session.getAttribute("restaurantName");

    }

    @RequestMapping(value = "/getInformation", method = RequestMethod.POST)
    public Map<String,Object> getInformation(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Map result=new HashMap();
        Restaurant restaurant=userDao.getRestaurantByID((String)session.getAttribute("restaurantID"));
        result.put("restaurantID", restaurant.getRestaurantID());
        result.put("restaurantName", restaurant.getName());
        result.put("type", restaurant.getType());
        result.put("password", restaurant.getPassword());
        result.put("address", restaurant.getAddress());
        return result;
    }

    @RequestMapping(value = "/Inform", method = RequestMethod.POST)
    public String inform(HttpServletRequest request) {
        HttpSession session=request.getSession();
        String result=messageService.getExamineResult((String)session.getAttribute("restaurantID"));
        return result;
    }

    @RequestMapping(value = "/getMenu", method = RequestMethod.POST)
    public List<Menu> getMenu(HttpServletRequest request) {
        HttpSession session=request.getSession();
        List<Menu> result=menuService.getNowMenu((String)session.getAttribute("restaurantID"));
        return result;
    }

    @RequestMapping(value = "/updateMenu", method = RequestMethod.POST)
    public void updateMenu(HttpServletRequest request, @RequestBody List<Menu> menuList) {
        HttpSession session=request.getSession();
        for (Menu menu:menuList){
            menu.setRestaurantID((String)session.getAttribute("restaurantID"));
        }
        System.out.println("up");
        menuService.insertMenu(menuList);
    }

}
