package com.bxl;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


/**
 * register-center 即 eureka 注册中心
 *
 * 注意：
 * 说明注册中心的服务列表，在本机是有缓存的，这跟dubbo/dubbox类似。
 */
@SpringBootApplication
@EnableEurekaServer  //启动一个服务注册中心
public class Regiest01Application {

	public static void main(String[] args) {
		//SpringApplication.run(Regiest01Application.class, args);
		new SpringApplicationBuilder(Regiest01Application.class).web(true).run(args);
	}
}
