package com.hungry.serviceImpl;

import com.hungry.dao.MenuDao;
import com.hungry.model.Menu;
import com.hungry.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuDao menuDao;

    @Override
    public void insertMenu(List<Menu> menuList) {
        menuDao.insertMenu(menuList);
    }

    @Override
    public List<Menu> getMenu(String restaurantID) {
        return menuDao.getMenu(restaurantID);
    }

    @Override
    public List<Menu> getNowMenu(String restaurantID) {
        List<Menu> menuList=menuDao.getMenu(restaurantID);
        List<Menu> result=new ArrayList<>();
        Date date=new Date();
        for (Menu menu:menuList){
            if(menu.getEndTime().getTime()>=date.getTime()){
                result.add(menu);
            }
        }
        return result;
    }
}
