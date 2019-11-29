package com.bxl.api;


import com.bxl.dto.AppConfig;
import com.bxl.service.AppConfigService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * 请求/fresh需要有几点要求：
 *      1.加actuator的依赖
 *      2.SpringCloud1.5以上需要设置 management.security.enabled=false
 *
 * 一、当config_server中心的配置文件内容有变化，手动调用 localhost:8002/refresh POST 刷新配置
 * 二、配置好消息总线后，调用http://xxx:port/bus/refresh POST 请求，则会刷新配置config-server的缓冲信息（每次刷新都会缓存最新的所有配置信息），
 *      凡是依赖动态配置的微服务模块都会被更新
 */
@RefreshScope
@RestController
@RequestMapping("/app")
public class AppConfingApi {

    private static final Logger logger = LoggerFactory.getLogger(AppConfingApi.class);
    @Value("${info.version}")
    private String version;
    @Value("${info.desc}")
    private String desc;
    @Value("${info.status}")
    private String status;
    @Autowired
    private AppConfigService appConfigService;


    @GetMapping("/row")
    public List<AppConfig> findUser() {
        return appConfigService.findAll();
    }

    @GetMapping(value = "/intro", produces = {"application/json;charset=UTF-8"})
    public Map<String,Object> intro(){
        Map<String,Object> map = new HashMap();
        map.put("version",version);
        map.put("desc",desc);
        map.put("status",status);
        System.out.println("查询服务简介完成！");
        return map;
    }

    @RequestMapping(value = "/findAppWithIds", method = GET)
    @HystrixCommand(fallbackMethod = "findAppWithIdsToError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    })
    public List<AppConfig> findAppWithIds (Integer[] ids)  {
        List<AppConfig> list = new ArrayList<>(16);
        try{
            list = appConfigService.findAppWithIds(ids);
            int x = new Random().nextInt(2000);
            if (x > 1500){
                throw new RuntimeException("App列表查询出现未知异常！！！");
            }
        }catch (Exception e) {
            logger.error("查询App列表异常！",e);
            throw e;
        }
        return list;
    }

    //服务降级
    //注意：目标方法和回退方法参数和返回类型要一致，并且必须定义在同一个类中
    public List<AppConfig> findAppWithIdsToError(Integer[] ids){
        List<AppConfig> list = new ArrayList<>(16);
        logger.info("本次app信息查询异常，进入服务降级！！！");
        return list;
    }



}
