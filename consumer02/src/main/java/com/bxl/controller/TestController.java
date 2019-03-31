package com.bxl.controller;

//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by root on 2019/3/4.
 */
@RestController
public class TestController {


    @RequestMapping("/hi")
    @HystrixCommand(fallbackMethod = "hiError")
    public String home(@RequestParam Integer age) {
        int i = 100/age;
        return "age is " + age;
    }

    public String hiError(Integer age) {
        return "age is " + age + ",sorry,error!";
    }

}
