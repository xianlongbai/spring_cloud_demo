package com.bxl.controller.interfaces;

import com.bxl.controller.BasicAuthConfiguration;
import com.bxl.service.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by root on 2018/3/21.
 * 默认配置类:FeignClientsConfiguration
 */

//有认证的方式
@FeignClient(name = "service-provider-demo", configuration = BasicAuthConfiguration.class)
public interface UserFeignClient {

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    UserDTO findUser(@PathVariable("id") Integer userId);
}


//@FeignClient(name = "service-provider-demo")
//public interface UserFeignClient {
//
//    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
//    UserDTO findUser(@PathVariable("id") Integer userId);
//}
