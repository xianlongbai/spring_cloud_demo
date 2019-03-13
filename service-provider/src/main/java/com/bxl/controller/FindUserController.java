package com.bxl.controller;

import com.bxl.service.UserDTO;
import com.bxl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by root on 2018/3/21.
 */

@RestController
public class FindUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserDTO findUser(@PathVariable Integer id) {
        return userService.findUser(id);
    }

}
