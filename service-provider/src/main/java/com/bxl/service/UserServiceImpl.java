package com.bxl.service;

import com.bxl.service.UserDTO;
import com.bxl.service.UserService;
import org.springframework.stereotype.Service;

/**
 * Created by root on 2018/3/20.
 */


@Service("userService")
public class UserServiceImpl implements UserService {


    @Override
    public UserDTO findUser(Integer userId) {

        UserDTO userDTO = new UserDTO();

        if (userId==1){
            userDTO.setUserId(1);
            userDTO.setUserName("baixianlong");
        }else {
            userDTO.setUserId(2);
            userDTO.setUserName("凡人");
            int a = 1/0;
        }
        return userDTO;
    }
}
