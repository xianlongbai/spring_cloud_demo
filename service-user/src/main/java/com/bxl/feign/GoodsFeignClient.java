package com.bxl.feign;

import com.bxl.dto.GoodsConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "SERVICE-GOODS")
public interface GoodsFeignClient {

    @RequestMapping(value = "/goods/findGoods", method = RequestMethod.GET)
    Map<String,Object> findJxConfig(@RequestParam("gid") Integer gid);

}
