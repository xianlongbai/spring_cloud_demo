package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * service-provider 为服务提供方
 */
@SpringBootApplication
//表明这是一个eureka的客户端程序(即：能向eureka server注册）
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class ServiceUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceUserApplication.class, args);
	}
}
