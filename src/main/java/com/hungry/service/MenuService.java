package com.hungry.service;

import com.hungry.model.Menu;

import java.util.List;

public interface MenuService {

    public void insertMenu(List<Menu> menuList);
    public List<Menu> getMenu(String restaurantID);
    public List<Menu> getNowMenu(String restaurantID);
}
