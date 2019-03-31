package com.bxl.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by root on 2019/3/31.
 */
@ApiModel
public class User {

    @ApiModelProperty(value="ID",dataType="String",name="ID",example="0001")
    private String id;

    @ApiModelProperty(value="姓名",dataType="String",name="name",example="bxl")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(value="年龄",dataType="int",name="age",example="28")
    private int age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
