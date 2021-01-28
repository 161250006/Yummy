package com.hungry.service;

import com.hungry.model.Modify;

import java.util.List;

public interface MessageService {

    public void approveMessage(Integer messageID,String result,String restaurantID);
    public List<Modify> showMessage();
    public String getExamineResult(String restaurantID);
}
