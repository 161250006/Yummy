package com.hungry.controller;

import com.hungry.dao.UserDao;
import com.hungry.model.Address;
import com.hungry.model.Restaurant;
import com.hungry.model.User;
import com.hungry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;


    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public Map<String,String> register(HttpServletRequest request, @RequestParam("userName") String userName, @RequestParam("password") String password,
                                       @RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("address") String[] address, @RequestParam("passwordCertain") String passwordCertain) {
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
        userService.modifyUserImformation(userName, password, name, phoneNumber, address);
        session.setAttribute("name", name);
        return resultMap;
    }


    @RequestMapping(value = "/getName", method = RequestMethod.POST)
    public String getName(HttpServletRequest request) {
        HttpSession session=request.getSession();
        return (String)session.getAttribute("name");
    }

    @RequestMapping(value = "/getInformation", method = RequestMethod.POST)
    public Map<String,Object> getInformation(HttpServletRequest request) {
        HttpSession session=request.getSession();
        Map result=new HashMap();
        User user=userDao.getUserByUserName((String)session.getAttribute("userName"));
        List<String> addressList=userService.getAddress((String)session.getAttribute("userName"));
        result.put("userName", user.getUserName());
        result.put("password", user.getPassword());
        result.put("name", user.getName());
        result.put("phoneNumber", user.getPhoneNumber());
        result.put("address", addressList);
        result.put("level", user.getLevel()+"");
        result.put("balance", user.getBalance()+"");
        return result;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public void deleteUser(HttpServletRequest request) {
        HttpSession session=request.getSession();
        userService.deleteUser((String)session.getAttribute("userName"));
        session.removeAttribute("userName");
        session.removeAttribute("name");
    }

    @RequestMapping(value = "/showRestaurant", method = RequestMethod.POST)
    public List<Restaurant> showReastaurant(HttpServletRequest request) {
        return userService.showRestaurant();
    }

    @RequestMapping(value = "/searchRestaurant", method = RequestMethod.POST)
    public List<Restaurant> searchRestaurant(HttpServletRequest request,@RequestParam("input") String input) {
        return userService.searchRestaurant(input);
    }

    @RequestMapping(value = "/getLevel", method = RequestMethod.POST)
    public String getLevel(HttpServletRequest request) {
        HttpSession session=request.getSession();
        User user=userDao.getUserByUserName((String)session.getAttribute("userName"));
        return String.valueOf(user.getLevel());
    }

    @RequestMapping(value = "/getAddress", method = RequestMethod.POST)
    public List<String> getAddress(HttpServletRequest request) {
        HttpSession session=request.getSession();
        List<String> address=userService.getAddress((String)session.getAttribute("userName"));
        return address;
    }

    @RequestMapping(value = "/getBalance", method = RequestMethod.POST)
    public String getBalance(HttpServletRequest request) {
        HttpSession session=request.getSession();
        User user=userDao.getUserByUserName((String)session.getAttribute("userName"));
        return String.valueOf(user.getBalance());
    }
}
