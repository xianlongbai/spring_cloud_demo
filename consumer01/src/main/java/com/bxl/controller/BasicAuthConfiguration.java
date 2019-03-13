package com.bxl.controller;

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
}
