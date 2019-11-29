package com.bxl.feign;


import com.bxl.lowfeign.JxFeignClientToError;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

//@FeignClient(name = "SERVICE-JX",fallback = JxFeignClientToError.class)
@FeignClient(name = "SERVICE-JX")
public interface JxFeignClient {

    @RequestMapping(value = "/jx/findJxConfig", method = RequestMethod.GET)
    Map<String,Object> findJxConfig(@RequestParam("uid") Integer uid);

}
