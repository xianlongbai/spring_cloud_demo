package com.bxl.service;


import com.bxl.dto.GoodsConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GoodsService {

    //模拟数据
    private final static Map<Integer, GoodsConfig> map;
    static {
        map = new HashMap();
        GoodsConfig goods01 = new GoodsConfig(1, "相机");
        GoodsConfig goods02 = new GoodsConfig(2, "iphone");
        GoodsConfig goods03 = new GoodsConfig(3, "微波炉");
        map.put(1,goods01);
        map.put(2,goods02);
        map.put(3,goods03);
    }

    public GoodsConfig findGoods(Integer gid) {
        int x = new Random().nextInt(2000);
        if (x > 1500){
            throw new RuntimeException("商品查询出现未知异常！！！");
        }
        return map.get(gid);
    }


}
