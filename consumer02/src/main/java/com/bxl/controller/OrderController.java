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
 * Created by root on 2018/3/21.
 *
 * http://xxx:8001/log-instance  可以用来查看负载均衡的情况
 *
 */


//使用ribbon+restTemplate
@RestController
public class OrderController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient loadBalancerClient;  //具有负载均衡的能力

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/order/{userId}/{orderNo}", produces = {"application/json;charset=UTF-8"})
    public UserDTO findOrder(@PathVariable Integer userId, @PathVariable String orderNo) {
        //这里其实调用的是服务提供者的接口
        UserDTO user = restTemplate.getForEntity("http://SERVICE-PROVIDER-DEMO/user/" + userId, UserDTO.class).getBody();
        System.out.println(2222);
        return user;
    }

    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo() {
        return this.discoveryClient.getInstances("SERVICE-PROVIDER-DEMO");
    }

    @GetMapping("/log-instance")
    public ServiceInstance chooseInstance() {
        return this.loadBalancerClient.choose("SERVICE-PROVIDER-DEMO");
    }


    @RequestMapping("/miya")
    public String info() {
        System.out.println("这是开始服务");
        return restTemplate.getForObject("http://CONSUMER-FEIGN/student/info", String.class);
    }


    //指定100%输出日志，效果跟spring.sleuth.sampler.percentage=1是一样的
//    @Bean
//    public AlwaysSampler defaultSampler() {
//        return new AlwaysSampler();
//    }

}
