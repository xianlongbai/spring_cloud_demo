package com.bxl.controller;

import com.bxl.dto.AppConfig;
import com.bxl.dto.GoodsConfig;
import com.bxl.service.GoodsService;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * Created by root on 2018/3/21.
 *
 */


@RestController
@RequestMapping("/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/findGoods", method = GET)
    @HystrixCommand(fallbackMethod = "findGoodsToError")
    public Map<String, Object> findGoods (Integer gid) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        try{
            GoodsConfig goods = goodsService.findGoods(gid);
            resMap.put("data",goods);
            resMap.put("code", 200);
        }catch (Exception e) {
            resMap.put("code", 400);
            throw e;
        }
        return resMap;
    }

    //服务降级
    //注意：目标方法和回退方法参数和返回类型要一致，并且必须定义在同一个类中
    public Map<String, Object> findGoodsToError(Integer gid){
        Map<String, Object> resMap = new HashMap<String, Object>();
        logger.info("本次商品查询出现未知异常，进行服务降级！！！");
        resMap.put("data",null);
        resMap.put("code",250);
        return resMap;
    }


}
