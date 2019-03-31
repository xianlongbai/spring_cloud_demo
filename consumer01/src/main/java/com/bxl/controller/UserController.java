package com.bxl.controller;

import com.bxl.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * Created by root on 2019/3/31.
 */

@RestController
@RequestMapping("/user")
@Api(tags="用户API")
public class UserController {


    @RequestMapping(value = "/findById/{id}",method = RequestMethod.GET)
    @ApiOperation(value="用户查询(ID)")
    @ApiImplicitParam(name="id",value="查询ID",required=true)
    public String findById(@PathVariable("id") String id){
        return "xianlongbai_"+id;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value="新增用户")
    public String findById(@RequestBody User user){
        if (user.getName()!=null){
            return "新增成功！";
        }
        return "新增失败！";
    }

}
