package com.bxl.config;

import feign.Retryer;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * Created by root on 2018/3/21.
 */
public class BasicAuthConfiguration {

    @Bean
    public BasicAuthRequestInterceptor basicAuthorizationInterceptor() {
        return new BasicAuthRequestInterceptor("admin", "admin");
    }

    //更改FeignClient的重试次数
//    @Bean
//    public Retryer feignRetryer() {
//        return new Retryer.Default(100, 1L, 5);
//    }
}
