package com.bxl.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by root on 2019/3/7.
 *
 * 注意：
 * 当config server中心的配置文件内容有变化，手动调用 localhost:8002/refresh POST 刷新配置
 * 请求/fresh需要有几点要求：1.加actuator的依赖 2.SpringCloud1.5以上需要设置 management.security.enabled=false
 *
 * 配置好消息总线后，调用http://xxx:port/bus/refresh POST 请求,其它模块依赖此配置的也会更新
 */
@RestController
@RefreshScope     //动态拿到configserver 配置信息
@RequestMapping("/dc")
public class DynamicParam {

//    @Autowired
//    DemoConfig config;

    @Value("${student.name}")
    private String name;

    @Value("${student.age}")
    private Integer age;


    @RequestMapping("/test01")
    public String test01(){
        System.out.println("测试分布式配置....");
        return "consumer02 ：name="+name+",age="+age+"!!!";
    }

//    @RequestMapping("/test")
//    public String test(){
//        return config.getName()+","+config.getAge();
//    }



}
