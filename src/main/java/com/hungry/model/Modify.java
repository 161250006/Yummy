package com.hungry.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="modify")
public class Modify {

    @Id
    @Column(name = "modify_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer modifyID;

    @Column(name = "restaurant_id")
    private String restaurantID;

    @Column(name = "restaurantName")
    private String restaurantName;

    @Column(name = "password")
    private String password;

    @Column(name = "restaurantType")
    private String type;

    @Column(name = "restaurantAddress")
    private String address;

    @Column(name = "state")
    private String state;

    @Column(name = "time")
    private Date time;

    @Column(name = "ifInform")
    private int ifInform;

    public Integer getModifyID() {
        return modifyID;
    }

    public void setModifyID(Integer modifyID) {
        this.modifyID = modifyID;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getIfInform() {
        return ifInform;
    }

    public void setIfInform(int ifInform) {
        this.ifInform = ifInform;
    }
}
