package com.bxl.service;

import com.bxl.dto.UserDTO;

import java.util.Map;

/**
 * Created by root on 2018/3/20.
 */
public interface UserService {

    UserDTO findUser(Integer userId);

    Map<String, Object> findUserDetail(Integer id);

    Map<String, Object> findShopDetail(Integer id);
}
