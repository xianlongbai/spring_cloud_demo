package com.bxl.config;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by root on 2019/10/31.
 */
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule() {
        //return new RoundRobinRule();//轮询算法
        //return new RandomRule();//随机算法
        return new CustomRule();
    }

}
