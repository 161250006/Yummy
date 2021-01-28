package com.hungry.dao;

import com.hungry.model.Modify;
import com.hungry.model.Restaurant;

import java.util.List;

public interface MessageDao {

    public void insertMessage(Modify modify);
    public String getExamineResult(String restaurantID);
    public List<Modify> getAllModify();
    public void approve(Integer modifyID,String result);
}
