package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer  //启动一个服务注册中心
public class Regiest02Application {

	public static void main(String[] args) {
		//SpringApplication.run(Regiest02Application.class, args);
		new SpringApplicationBuilder(Regiest02Application.class).web(true).run(args);
	}
}
