package com.bxl.dto;

import java.io.Serializable;

/**
 * Created by root on 2018/3/20.
 */

public class UserDTO implements Serializable {

    private Integer userId;
    private String userName;
    private Integer age;

    public UserDTO() {
    }

    public UserDTO(Integer userId, String userName, Integer age) {
        this.userId = userId;
        this.userName = userName;
        this.age = age;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
