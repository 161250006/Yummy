package com.hungry.serviceImpl;

import com.hungry.dao.MessageDao;
import com.hungry.dao.UserDao;
import com.hungry.model.Modify;
import com.hungry.model.Restaurant;
import com.hungry.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageDao messageDao;

    @Autowired
    UserDao userDao;

    @Override
    public void approveMessage(Integer messageID, String result,String restaurantID) {
        if(result.equals("通过")){
            System.out.println(restaurantID);
            Restaurant restaurant=userDao.getRestaurantByID(restaurantID);
            System.out.println(restaurant.getRestaurantID());
            userDao.modifyRestaurant(restaurant);
        }
        messageDao.approve(messageID, result);
    }

    @Override
    public List<Modify> showMessage() {
        return messageDao.getAllModify();
    }

    @Override
    public String getExamineResult(String restaurantID) {
        return messageDao.getExamineResult(restaurantID);
    }
}
