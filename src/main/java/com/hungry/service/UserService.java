package com.hungry.service;

import com.hungry.model.Restaurant;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    public void sendMail(String userName,HttpServletRequest request);
    public boolean register(String userName,String password,String name,String phoneNumber,String[] address);
    public String restaurantRegister(String restaurantName,String password,String type,String address);
    public String login(String userName,String password);
    public String restaurantLogin(String restaurantID,String password);
    public String managerLogin(String userName,String password);
    public void modifyUserImformation(String userName,String password,String name,String phoneNumber,String[] address);
    public void modifyRestaurantImformation(String restaurantName,String password,String type,String address,String restaurantID);
    public List<String> getAddress(String userName);
    public void deleteUser(String userName);
    public List<Restaurant> searchRestaurant(String input);
    public List<Restaurant> showRestaurant();
}
