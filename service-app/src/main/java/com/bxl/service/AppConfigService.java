package com.bxl.service;

import com.bxl.dto.AppConfig;
import com.bxl.dto.JxConfig;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppConfigService {


    //模拟数据
    private final static Map<Integer, AppConfig> map;
    static {
        map = new HashMap();
        AppConfig app01 = new AppConfig(1, "微信");
        AppConfig app02 = new AppConfig(2, "QQ");
        AppConfig app03 = new AppConfig(3, "饿了吗");
        AppConfig app04 = new AppConfig(4, "蚂蚁财富");
        AppConfig app05 = new AppConfig(5, "淘宝");
        map.put(1,app01);
        map.put(2,app02);
        map.put(3,app03);
        map.put(4,app04);
        map.put(5,app05);
    }

    public List<AppConfig> findAll() {
        List<AppConfig> list = new ArrayList();
        AppConfig app1 = new AppConfig(1, "微信");
        AppConfig app2 = new AppConfig(2, "QQ");
        AppConfig app3 = new AppConfig(3, "百度");
        list.add(app1);
        list.add(app2);
        list.add(app3);
        return list;
    }


    public List<AppConfig> findAppWithIds(Integer[] ids) {
        List<AppConfig> list = new ArrayList();
        for (Integer id : ids) {
            list.add(map.get(id));
        }
        return list;
    }

}
