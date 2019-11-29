package com.bxl.feign;

import com.bxl.dto.OrderConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "SERVICE-ORDER")
public interface OrderFeignClient {

    @RequestMapping(value = "/order/findOrder", method = RequestMethod.GET)
    OrderConfig findOrder(@RequestParam("uid") Integer uid);

}
