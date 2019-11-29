package com.bxl.service;


import com.bxl.dto.AppConfig;
import com.bxl.dto.JxConfig;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JxConfigService {

    //模拟数据
    private final static Map<Integer,JxConfig> map;
    static {
        map = new HashMap();
        JxConfig jx01 = new JxConfig(1, "iphone",1,new Integer[]{1,2,3});
        JxConfig jx02 = new JxConfig(2, "华为",2,new Integer[]{2,3,4});
        JxConfig jx03 = new JxConfig(3, "小米",3,new Integer[]{1,3,5});
        map.put(1,jx01);
        map.put(2,jx02);
        map.put(3,jx03);
    }


    public JxConfig findJxConfig(Integer uid) {
        int x = new Random().nextInt(2000);
        if (x > 1500){
            throw new RuntimeException("机型详情查询出现未知异常！！！");
        }
        return map.get(uid);
    }


}
