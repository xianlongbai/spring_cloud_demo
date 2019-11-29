package com.bxl.dto;

import java.io.Serializable;

public class AppConfig implements Serializable {

    private Integer id;
    private String name;

    public AppConfig() {
    }

    public AppConfig(Integer id, String name) {
        this.id = id;
        this.name = name;
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

}
