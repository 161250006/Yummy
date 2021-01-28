package com.hungry.serviceImpl;

import com.hungry.dao.OrderDao;
import com.hungry.dao.UserDao;
import com.hungry.service.StatisticsService;
import com.hungry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    UserDao userDao;

    @Override
    public Map<String, Integer> getStatisticsData() {
        Map<String,Integer> result;
        result=orderDao.getAllMoney();
        result.put("userNumber", userDao.getUserNumber());
        result.put("restaurantNumber", userDao.getRestaurantNumber());
        return result;
    }
}
