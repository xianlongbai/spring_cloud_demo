package com.bxl.controller;

import com.bxl.service.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 消费者--01：
 * Created by root on 2018/3/21.
 * http://xxx:8001/getOneInstance  可以用来查看负载均衡的情况
 *
 */
@RestController
@RequestMapping("/ribbon")
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    //具有负载均衡能力的eureka客户端
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/order/{userId}/{orderNo}", produces = {"application/json;charset=UTF-8"})
    public UserDTO findOrder(@PathVariable Integer userId, @PathVariable String orderNo) {
        //这里其实调用的是服务提供者的接口
        UserDTO user = restTemplate.getForEntity("http://SERVICE-PROVIDER-DEMO/user/" + userId, UserDTO.class).getBody();
        System.out.println(1111);
        //        if (user != null) {
//            return user.getUserName() + " 的订单" + orderNo + " 找到啦！";
//        }

//        return "用户不存在！";
        return user;
    }


    /**
     * discoveryClient.getInstances会拿到服务的所有注册实例列表(正是有了次列表才能配置相应的请求策略)
     */
    @GetMapping("/getAllInstances")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("SERVICE-PROVIDER-DEMO");
    }

    /**
     * 根据此时服务配置的请求策略获得相应的服务实例,如：@RibbonClient(name="SERVICE-PROVIDER-DEMO", configuration = MySelfRule.class)
     * @return
     */
    @GetMapping("/getOneInstance")
    public ServiceInstance chooseInstance() {
        return this.loadBalancerClient.choose("SERVICE-PROVIDER-DEMO");
    }

}
