package com.hungry.controller;

import com.hungry.dao.UserDao;
import com.hungry.model.Restaurant;
import com.hungry.model.User;
import com.hungry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Index")
public class IndexController {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public void init(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (null == session) {
            session = request.getSession(true);
        }
    }


    @RequestMapping(value = "/getMail", method = RequestMethod.POST)
    public void getCode(HttpServletRequest request,@RequestParam String userName) {
        userService.sendMail(userName, request);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Map<String,String> register(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("code") String code, @RequestParam("password") String password,
                        @RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("address") String[] address, @RequestParam("passwordCertain") String passwordCertain) {
        Map<String,String> resultMap=new HashMap<>();
        String result="";
        HttpSession session=request.getSession();
        String codeNumber = (String)session.getAttribute("code");
        if(!password.equals(passwordCertain)){
            result="uncertain";
            resultMap.put("result", result);
            return resultMap;
        }
        if(!code.equals(codeNumber)){
            result="codeFalse";
            resultMap.put("result", result);
            return resultMap;
        }
        boolean ifSuccess= userService.register(userName, password, name, phoneNumber, address);
        if (ifSuccess) {
            resultMap.put("result", "success");
        }
        else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }


    @RequestMapping(value = "/restaurantRegister", method = RequestMethod.POST)
    public Map<String,String> restaurantRegister(HttpServletRequest request, @RequestParam("name") String restaurantName, @RequestParam("type") String type, @RequestParam("password") String password,
                                         @RequestParam("address") String address, @RequestParam("passwordCertain") String passwordCertain) {
        Map<String,String> resultMap=new HashMap<>();
        String result="";
        resultMap.put("result", "success");
        if(!password.equals(passwordCertain)){
            result="uncertain";
            resultMap.put("result", result);
            return resultMap;
        }
        String codeID= userService.restaurantRegister(restaurantName,password, type,address);
        resultMap.put("codeID", codeID);
        return resultMap;
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String,String> login(HttpServletRequest request, @RequestParam("userName") String userName,  @RequestParam("password") String password) {
        Map<String,String> resultMap=new HashMap<>();
        String result= userService.login(userName, password);
        HttpSession session=request.getSession();
        resultMap.put("result", result);
        if (result.equals("success")) {
            User user=userDao.getUserByUserName(userName);
            session.setAttribute("userID", user.getUserId());
            session.setAttribute("userName", userName);
            session.setAttribute("name", user.getName());
        }
        return resultMap;
    }

    @RequestMapping(value = "/restaurantLogin", method = RequestMethod.POST)
    public Map<String,String> restaurantLogin(HttpServletRequest request, @RequestParam("codeID") String codeID,  @RequestParam("password") String password) {
        Map<String,String> resultMap=new HashMap<>();
        String result= userService.restaurantLogin(codeID, password);
        HttpSession session=request.getSession();
        resultMap.put("result", result);
        if (result.equals("success")) {
            Restaurant restaurant=userDao.getRestaurantByID(codeID);
            session.setAttribute("restaurantName", restaurant.getName());
            session.setAttribute("restaurantID", restaurant.getRestaurantID());
        }
        return resultMap;
    }

    @RequestMapping(value = "/managerLogin", method = RequestMethod.POST)
    public Map<String,String> managerLogin(HttpServletRequest request, @RequestParam("userName") String userName,  @RequestParam("password") String password) {
        Map<String,String> resultMap=new HashMap<>();
        String result= userService.managerLogin(userName, password);
        HttpSession session=request.getSession();
        resultMap.put("result", result);
        if (result.equals("success")) {
            session.setAttribute("userName", userName);
        }
        return resultMap;
    }

}
