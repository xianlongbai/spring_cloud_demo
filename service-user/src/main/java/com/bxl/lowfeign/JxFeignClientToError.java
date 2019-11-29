package com.bxl.lowfeign;

import com.bxl.feign.JxFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * feign 的熔断实现
 */
//@Component
public class JxFeignClientToError implements JxFeignClient {

    private static final Logger logger = LoggerFactory.getLogger(JxFeignClientToError.class);

    @Override
    public Map<String, Object> findJxConfig(Integer uid) {
        Map<String,Object> map = new HashMap();
        logger.info("user用户详情查询异常，进入服务降级！");
//        map.put("userInfo","服务异常！");
//        map.put("phoneInfo","服务异常！");
        map.put("data","服务异常");
        map.put("code",250);
        return map;
    }

}
