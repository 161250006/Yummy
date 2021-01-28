package com.hungry.service;

import com.hungry.model.Orders;

import java.util.List;

public interface OrderService {

    public void insertOrder(Orders orders);
    public List<Orders> getAllOrdersUser(String userName);
    public List<Orders> getAllOrdersRestaurant(String restaurantID);
    public void cancelOrder(int OrderID);
    public void payOrder(int OrderID);
    public void unsubscribeOrder(int OrderID,int unsubscribeMoney);
    public void confirmOrder(int OrderID);
    public boolean ifOutOfTime(int OrderID);
}
