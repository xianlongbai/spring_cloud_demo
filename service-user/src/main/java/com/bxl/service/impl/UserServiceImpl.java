package com.bxl.service.impl;


import com.bxl.dto.OrderConfig;
import com.bxl.dto.UserDTO;
import com.bxl.feign.GoodsFeignClient;
import com.bxl.feign.JxFeignClient;
import com.bxl.feign.OrderFeignClient;
import com.bxl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 2018/3/20.
 */


@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private JxFeignClient jxFeignClient;
    @Autowired
    private OrderFeignClient orderFeignClient;
    @Autowired
    private GoodsFeignClient goodsFeignClient;

    @Override
    public UserDTO findUser(Integer userId) {
        UserDTO userDTO = new UserDTO();
        if (userId==1){
            userDTO.setUserId(1);
            userDTO.setUserName("白先龙");
            userDTO.setAge(29);
        }else if(userId==2){
            userDTO.setUserId(2);
            userDTO.setUserName("赵欣欣");
            userDTO.setAge(25);
        }else if(userId==3){
            userDTO.setUserId(3);
            userDTO.setUserName("李冰冰");
            userDTO.setAge(39);
        }else {
            return null;
        }
        return userDTO;
    }

    @Override
    public Map<String, Object> findUserDetail(Integer id) {
        Map<String,Object> map = new HashMap();
        UserDTO user = this.findUser(id);
        Map<String, Object> resMap = jxFeignClient.findJxConfig(user.getUserId());
        map.put("userInfo",user);
        map.put("phoneInfo",resMap.get("data"));
        return map;
    }

    @Override
    public Map<String, Object> findShopDetail(Integer id) {
        Map<String,Object> map = new HashMap();
        UserDTO user = this.findUser(id);
        OrderConfig order = orderFeignClient.findOrder(user.getUserId());
        Map<String, Object> jxConfig = goodsFeignClient.findJxConfig(order.getGid());
        map.put("userInfo",user);
        map.put("goodsInfo",jxConfig.get("data"));
        return map;
    }


}
