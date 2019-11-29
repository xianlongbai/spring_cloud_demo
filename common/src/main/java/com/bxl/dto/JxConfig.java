package com.bxl.dto;

import java.io.Serializable;

public class JxConfig implements Serializable {

    private Integer id;
    private String name;
    private Integer uid;
    private Integer[] appIds;
    private AppConfig[] appArray;

    public JxConfig() {
    }

    public JxConfig(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public JxConfig(Integer id, String name, Integer uid, Integer[] appIds) {
        this.id = id;
        this.name = name;
        this.uid = uid;
        this.appIds = appIds;
    }

    public JxConfig(Integer id, String name, Integer uid, Integer[] appIds, AppConfig[] appArray) {
        this.id = id;
        this.name = name;
        this.uid = uid;
        this.appIds = appIds;
        this.appArray = appArray;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer[] getAppIds() {
        return appIds;
    }

    public void setAppIds(Integer[] appIds) {
        this.appIds = appIds;
    }

    public AppConfig[] getAppArray() {
        return appArray;
    }

    public void setAppArray(AppConfig[] appArray) {
        this.appArray = appArray;
    }

}
