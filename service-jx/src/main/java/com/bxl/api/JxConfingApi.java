package com.bxl.api;

import com.bxl.dto.AppConfig;
import com.bxl.dto.JxConfig;
import com.bxl.service.JxConfigService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/jx")
public class JxConfingApi {

    private static final Logger logger = LoggerFactory.getLogger(JxConfingApi.class);

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private JxConfigService jxConfigService;


    @RequestMapping(value = "/findJxConfig", method = GET)
    @HystrixCommand(fallbackMethod = "findJxConfigToError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
            @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true"),
            //@HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "false"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "10"),
            @HystrixProperty(name = "fallback.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),  //熔断器的休眠时间
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false")
    })
    public Map<String, Object> findJxConfig (Integer uid) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            JxConfig jxConfig = jxConfigService.findJxConfig(uid);
            logger.info("机型基本详情查询完成~");
            Map<String,Integer[]> param = new HashMap();
            param.put("ids",jxConfig.getAppIds());
            AppConfig[] appArray = restTemplate.getForEntity("http://SERVICEAPP/app/findAppWithIds?ids={ids}", AppConfig[].class, param).getBody();
            logger.info("机型安装详情查询完成~");
            jxConfig.setAppArray(appArray);
            resMap.put("data",jxConfig);
            resMap.put("code", 200);
        }catch (Exception e) {
            logger.info("查詢機型詳情發生異常！！！",e);
            resMap.put("code", 400);
            throw e;
        }
        return resMap;
    }

    //服务降级
    //注意：目标方法和回退方法参数和返回类型要一致，并且必须定义在同一个类中
    public Map<String, Object> findJxConfigToError(Integer uid){
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("data",null);
        resMap.put("code",250);
        logger.info("本次机型详情查询出现未知异常，进行服务降级！！！");
        return resMap;
    }

}
