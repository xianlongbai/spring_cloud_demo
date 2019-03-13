package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


/**
 * service-provider 为服务提供方
 */
@SpringBootApplication
//表明这是一个eureka的客户端程序(即：能向eureka server注册）
@EnableDiscoveryClient
public class ServiceProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceProviderApplication.class, args);
	}
}
