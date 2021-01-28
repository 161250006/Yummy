package com.hungry.dao;

import com.hungry.model.Orders;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    public Map<String,Integer> getAllMoney();
    public void insertOrder(Orders orders);
    public List<Orders> getAllOrdersUser(String userName);
    public List<Orders> getAllOrdersRestaurant(String restaurantID);
    public void cancelOrder(int OrderID);
    public void payOrder(int OrderID);
    public void unsubscribeOrder(int OrderID);
    public void confirmOrder(int OrderID);
    public boolean ifOutOfTime(int OrderID);
    public Orders getOrder(int OrderID);
    public void changeRestaurantMoney(int OrderID,int unsubscribeMoney);
    public int getTotalBuyMoney(int userID);
}
