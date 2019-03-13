package com.bxl.controller;

import com.bxl.service.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by root on 2019/3/5.
 */

@RestController
//这里面的属性有可能会更新的，git中的配置中心变化的话就要刷新，没有这个注解内，配置就不能及时更新
//@RefreshScope
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private RestTemplate restTemplate;

//    @Value("${student.name}")
//    private String name;
//    @Value("${age}")
//    private Integer age;

//    @RequestMapping("/test")
//    public String test(){
//        return this.age+"";
//    }



    @RequestMapping("/info")
    public UserDTO info(){
        System.out.println("我是学生服务！！！");
        UserDTO user = restTemplate.getForEntity("http://SERVICE-PROVIDER-DEMO/user/" + 1, UserDTO.class).getBody();
        return user;
    }

}
