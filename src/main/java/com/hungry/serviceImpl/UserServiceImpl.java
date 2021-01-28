package com.hungry.serviceImpl;

import com.hungry.dao.MessageDao;
import com.hungry.dao.UserDao;
import com.hungry.model.*;
import com.hungry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    UserDao userDao;

    @Autowired
    MessageDao messageDao;

    @Override
    public void sendMail(String userName,HttpServletRequest request) {
        //验证码
        System.out.println("get");
        String codeNum = "";
        int [] numbers = {0,1,2,3,4,5,6,7,8,9};
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int next = random.nextInt(10000);//目的是产生足够随机的数，避免产生的数字重复率高的问题
            codeNum=codeNum+numbers[next%10];
        }


        HttpSession session = request.getSession(false);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("578045968@qq.com");
        message.setTo(userName);
        message.setSubject("Yummy注册验证码");
        message.setText("您本次注册验证码为"+codeNum+",请勿透露给其他人");
        mailSender.send(message);
        session.setAttribute("code", codeNum);
    }

    @Override
    public boolean register(String userName, String password, String name, String phoneNumber, String[] address) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setBalance(1000);
        user.setIfDelete(0);
        user.setLevel(1);
        ArrayList<Address> addressArrayList=new ArrayList<>();
        for(String a:address){
            if(a.isEmpty()){
                continue;
            }
            Address userAddress=new Address();
            userAddress.setUserName(user.getUserName());
            userAddress.setAddress(a);
            addressArrayList.add(userAddress);
        }
        boolean ifSame=userDao.ifSame(userName);
        if (ifSame){
            return false;
        }
        else {
            userDao.insertUser(user,addressArrayList);
            return true;
        }
    }

    @Override
    public String login(String userName, String password) {
        User user=userDao.getUserByUserName(userName);
        if(user==null){
            return "notFound";
        }
        else {
            if(password.equals(user.getPassword())){
                return "success";
            }
            else {
                return "wrong";
            }
        }
    }

    @Override
    public String restaurantRegister(String restaurantName, String password, String type, String address) {
        Restaurant restaurant = new Restaurant();
        int restaurantNumber = userDao.getRestaurantNumber()+1;
        String codeID=""+restaurantNumber;
        while (codeID.length()<7){
            codeID="0"+codeID;
        }
        restaurant.setRestaurantID(codeID);
        restaurant.setAddress(address);
        restaurant.setName(restaurantName);
        restaurant.setPassword(password);
        restaurant.setType(type);
        userDao.insertRestaurant(restaurant);
        return codeID;
    }

    @Override
    public String restaurantLogin(String restaurantID, String password) {
        Restaurant restaurant=userDao.getRestaurantByID(restaurantID);
        if(restaurant==null){
            return "notFound";
        }
        else {
            if (password.equals(restaurant.getPassword())) {
                return "success";
            } else {
                return "wrong";
            }
        }
    }

    @Override
    public String managerLogin(String userName, String password){
       Manager manager=userDao.getManager(userName);
            if(manager==null){
                return "notFound";
            }
            else {
                if (password.equals(manager.getPassword())) {
                    return "success";
                } else {
                    return "wrong";
                }
            }
    }

    @Override
    public void modifyUserImformation(String userName,String password,String name,String phoneNumber,String[] address) {
        User user = new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setBalance(1000);
        user.setIfDelete(0);
        user.setLevel(1);
        ArrayList<Address> addressArrayList=new ArrayList<>();
        for(String a:address){
            if(a.isEmpty()){
                continue;
            }
            Address userAddress=new Address();
            userAddress.setUserName(user.getUserName());
            userAddress.setAddress(a);
            addressArrayList.add(userAddress);
        }
            userDao.modifyUser(user,addressArrayList);
    }

    @Override
    public List<String> getAddress(String userName) {
        List<Address> addresses=userDao.getAddressByUserName(userName);
        List<String> address=new ArrayList<>();
        for (Address a:addresses){
            address.add(a.getAddress());
        }
        return address;
    }

    @Override
    public void modifyRestaurantImformation(String restaurantName, String password, String type, String address,String restaurantID) {
        Modify modify=new Modify();
        Date date=new Date();
        modify.setRestaurantID(restaurantID);
        modify.setRestaurantName(restaurantName);
        modify.setPassword(password);
        modify.setType(type);
        modify.setAddress(address);
        modify.setTime(date);
        modify.setState("审核中");
        modify.setIfInform(0);
        messageDao.insertMessage(modify);
    }

    @Override
    public void deleteUser(String userName) {
        userDao.deleteUser(userName);
    }

    @Override
    public List<Restaurant> searchRestaurant(String input) {
        return userDao.searchRestaurant(input);
    }

    @Override
    public List<Restaurant> showRestaurant() {
        return userDao.showRestaurant();
    }
}
