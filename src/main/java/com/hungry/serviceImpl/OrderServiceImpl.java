package com.hungry.serviceImpl;

import com.hungry.dao.OrderDao;
import com.hungry.dao.UserDao;
import com.hungry.model.Orders;
import com.hungry.service.OrderService;
import com.hungry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Override
    public void insertOrder(Orders orders) {
        orderDao.insertOrder(orders);
    }

    @Override
    public void cancelOrder(int OrderID) {
        orderDao.cancelOrder(OrderID);
    }

    @Override
    public List<Orders> getAllOrdersUser(String userName) {
        List<Orders> orders=orderDao.getAllOrdersUser(userName);
        for (Orders o:orders){
            if (o.getState().equals("待支付")){
                boolean ifOutOfTime=ifOutOfTime(o.getOrderID());
                if (ifOutOfTime){
                    cancelOrder(o.getOrderID());
                    o.setState("已取消");
                }
            }
        }
        return orders;
    }

    @Override
    public List<Orders> getAllOrdersRestaurant(String restaurantID) {
        return orderDao.getAllOrdersRestaurant(restaurantID);
    }


    @Override
    public void payOrder(int OrderID) {
        Orders orders=orderDao.getOrder(OrderID);
        orderDao.payOrder(OrderID);
        userDao.payForMoney(orders.getUserID(), orders.getMoneyTotal());
    }

    @Override
    public void unsubscribeOrder(int OrderID,int unsubscribeMoney) {
        Orders orders=orderDao.getOrder(OrderID);
        userDao.getMoney(orders.getUserID(), unsubscribeMoney);
        orderDao.unsubscribeOrder(OrderID);
        orderDao.changeRestaurantMoney(OrderID, unsubscribeMoney);
    }

    @Override
    public void confirmOrder(int OrderID) {
        Orders orders=orderDao.getOrder(OrderID);
        orderDao.confirmOrder(OrderID);
        int totalMoney=orderDao.getTotalBuyMoney(orders.getUserID());
        int level=0;
        if (totalMoney<100){
            level=1;
        }
        else if (totalMoney<500){
            level=2;
        }
        else if (totalMoney<1000){
            level=3;
        }
        else if (totalMoney<5000){
            level=4;
        }
        else{
            level=5;
        }
        userDao.changeLevel(orders.getUserID(), level);
    }

    @Override
    public boolean ifOutOfTime(int OrderID) {
        return orderDao.ifOutOfTime(OrderID);
    }
}
