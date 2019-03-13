package com.bxl.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


import javax.sql.DataSource;

/**
 * Created by root on 2018/9/29.
 *
 * Spring Boot + Spring Security解决POST方式下的CSRF问题???
 * 例：HTTP Status 403－Invalid CSRF Token 'null' was found on the request parameter '_csrf' or header 'X-CSRF-TOKEN'
 * 解决方法：禁用csrf
 */
@Configuration
@EnableWebSecurity
//为什么要配置这个? 作用：开启注解赋权
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表示所有的访问都必须进行认证处理后才可以正常进行
        http.httpBasic()
                .and()
                .authorizeRequests()
                //.antMatchers("/user/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable();   //禁用csrf

        // 所有的Rest服务一定要设置为无状态，以提升操作性能
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //方式一：
    //指定可以等的用户名密码
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("admin")
                .roles("USER");
    }

    //方式二：
    //重写以下方法
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//            .withUser("admin").password("admin").roles("USER")
//            .and()
//            .withUser("test").password("test").roles("test");
//    }




}
