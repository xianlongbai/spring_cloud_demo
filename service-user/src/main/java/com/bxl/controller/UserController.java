package com.bxl.controller;

import com.bxl.dto.UserDTO;
import com.bxl.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by root on 2018/3/21.
 */

@RestController
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    //具有负载均衡能力的eureka客户端
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    //eureka客户端
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public UserDTO findUser(@PathVariable Integer id) {
        return userService.findUser(id);
    }

    //查询用户详情
    @RequestMapping(value = "/userDetail/{id}", method = GET, produces = {"application/json;charset=UTF-8"})
    public Map<String, Object> findUserDetail (@PathVariable Integer id) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            resMap = userService.findUserDetail(id);
            Thread.sleep(1000);
            resMap.put("code", 200);
        }catch (Exception e) {
            resMap.put("code", 400);
            logger.info("查询用户详情异常！",e);
        }
        return resMap;
    }

    //查询用户购物详情
    @RequestMapping(value = "/shopDetail/{id}", method = GET, produces = {"application/json;charset=UTF-8"})
    public Map<String, Object> findShopDetail (@PathVariable Integer id) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            resMap = userService.findShopDetail(id);
            resMap.put("code", 200);
        }catch (Exception e) {
            resMap.put("code", 400);
            logger.info("查询用户购物详情异常！",e);
        }
        return resMap;
    }


    //指定100%输出日志，效果跟spring.sleuth.sampler.percentage=1是一样的
//    @Bean
//    public AlwaysSampler defaultSampler() {
//        return new AlwaysSampler();
//    }

    /**
     * discoveryClient.getInstances会拿到服务的所有注册实例列表(正是有了次列表才能配置相应的请求策略)
     */
    @GetMapping("/getAllInstances")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("SERVICE-USER");
    }

    /**
     * 根据此时服务配置的请求策略获得相应的服务实例,如：@RibbonClient(name="SERVICE_USER", configuration = MySelfRule.class)
     * @return
     */
    @GetMapping("/getOneInstance")
    public ServiceInstance chooseInstance() {
        return this.loadBalancerClient.choose("SERVICE-USER");
    }


}
