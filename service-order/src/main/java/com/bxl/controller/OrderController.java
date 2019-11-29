package com.bxl.controller;

import com.bxl.dto.OrderConfig;
import com.bxl.dto.UserDTO;
import com.bxl.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 2018/3/21.
 * http://xxx:8001/getOneInstance  可以用来查看负载均衡的情况
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;

    //测试Ribbon
    @GetMapping(value = "/{userId}/{orderNo}", produces = {"application/json;charset=UTF-8"})
    public Map<String,Object> findOrder(@PathVariable Integer userId, @PathVariable String orderNo) {
        Map<String,Object> map = new HashMap();
        //这里其实调用的是服务提供者的接口
        UserDTO user = restTemplate.getForEntity("http://SERVICE-USER/user/" + userId, UserDTO.class).getBody();
        map.put("userInfo",user);
        map.put("orderNo", orderNo);
        return map;
    }

    @GetMapping(value = "/findOrder", produces = {"application/json;charset=UTF-8"})
    public OrderConfig findOrder(@RequestParam Integer uid) {
        return orderService.findOrder(uid);
    }






}
