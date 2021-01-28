package com.hungry.dao;

import com.hungry.model.Address;
import com.hungry.model.Manager;
import com.hungry.model.Restaurant;
import com.hungry.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserDao {

    public void insertUser(User user, ArrayList<Address> addressArrayList);
    public User getUserByUserName(String userName);
    public boolean ifSame(String userName);
    public void modifyUser(User user,ArrayList<Address> addressArrayList);
    public void modifyRestaurant(Restaurant restaurant);
    public Restaurant getRestaurantByID(String restaurantID);
    public void insertRestaurant(Restaurant restaurant);
    public Manager getManager(String userName);
    public int getUserNumber();
    public int getRestaurantNumber();
    public List<Address> getAddressByUserName(String userName);
    public void deleteUser(String userName);
    public List<Restaurant> searchRestaurant(String input);
    public List<Restaurant> showRestaurant();
    public void payForMoney(int userID,int money);
    public void getMoney(int userID,int money);
    public void changeLevel(int userID,int level);
}
