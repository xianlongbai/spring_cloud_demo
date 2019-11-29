package com.bxl.service;

import com.bxl.dto.JxConfig;
import com.bxl.dto.OrderConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    //模拟数据
    private final static Map<Integer, OrderConfig> map;
    static {
        map = new HashMap();
        OrderConfig order01 = new OrderConfig(1, 1,1);
        OrderConfig order02 = new OrderConfig(2, 2,2);
        OrderConfig order03 = new OrderConfig(3, 3,3);
        map.put(1,order01);
        map.put(2,order02);
        map.put(3,order03);
    }

    public OrderConfig findOrder(Integer uid) {
        return map.get(uid);
    }


}
