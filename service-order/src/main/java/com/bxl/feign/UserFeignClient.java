package com.bxl.feign;

import com.bxl.config.BasicAuthConfiguration;
import com.bxl.dto.UserDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by root on 2018/3/21.
 * 默认配置类:FeignClientsConfiguration
 *
 * 通过feign封装调用细节,暴露标准接口调用方式
 */

//有认证的方式
//注意：feign、Ribbon不支持下划线"_"，支持"-"，不然报错：Service id not legal hostname (SERVICE_USER)
@FeignClient(name = "SERVICE-USER", configuration = BasicAuthConfiguration.class)
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
