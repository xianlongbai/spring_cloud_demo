package com.bxl.controller;

import com.bxl.feign.UserFeignClient;
import com.bxl.dto.UserDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by root on 2018/3/21.
 *
 * Spring cloud有两种服务调用方式，一种是ribbon+restTemplate，另一种是feign
 * ribbon是一个负载均衡客户端，可以很好的控制htt和tcp的一些行为。Feign默认集成了ribbon
 */


@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    //使用feign方式
    @Autowired
    private UserFeignClient userFeignClient;

    private final Random rnd = new Random(System.currentTimeMillis());

    /**
     * 如果这个方法调用失败，会切换到备用方法findOrderFallback上，而且还设置了一个超时时间1000ms，
     * 如果findOrder方法没有在1s内返回结果，也算调用失败，同样会切换到备用方法findOrderFallback.
     * 注意：熔断的详细情况可以通过一下方式查看
     *      1、acturator提供了health端点来查看hystrix状态，http://localhost:xxxx/health
     *      2、通过监控 http://localhost:8002/hystrix.stream 可以查看详细的熔断情况
     * @param userId
     * @param orderNo
     * @return
     */
    @GetMapping("/{userId}/{orderNo}")
    @HystrixCommand(fallbackMethod = "findOrderFallback", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public String findOrder(@PathVariable Integer userId, @PathVariable String orderNo) throws InterruptedException {
        Thread.sleep(rnd.nextInt(2000));
        UserDTO user = userFeignClient.findUser(userId);
        if (user != null) {
            return user.getUserName() + " 的订单" + orderNo + " 找到啦!!!";
        }
        return "用户不存在！";
    }

    /**
     * 问题：todo...
     *  1、如果请求是zuul转发过来的，发生错误熔断是不能正确返回结果？
     * @param userId
     * @param orderNo
     * @return
     */
    public String findOrderFallback(Integer userId, String orderNo) {
        return "订单查找失败！";
    }

}
