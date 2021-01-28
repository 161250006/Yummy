package com.hungry.dao;

import com.hungry.model.Menu;

import java.util.List;

public interface MenuDao {

    public void insertMenu(List<Menu> menuList);
    public List<Menu> getMenu(String restaurantID);
}
