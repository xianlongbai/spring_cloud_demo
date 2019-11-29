package com.bxl.dto;

import java.io.Serializable;

public class OrderConfig implements Serializable {

    private Integer id;
    private Integer uid;
    private Integer gid;

    public OrderConfig() {
    }

    public OrderConfig(Integer id, Integer uid, Integer gid) {
        this.id = id;
        this.uid = uid;
        this.gid = gid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }
}
