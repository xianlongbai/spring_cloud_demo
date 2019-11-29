package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * 其实zuul实现负载均衡很简单，使用serviceId进行绑定后，如果有多个相同的serviceid，
 * 则会进行轮询的方式进行访问。
 * zuul的作用:
	 1、提供统一服务入口，微服务对前台透明
	 2、聚合后台服务，节省流量，提升性能
	 3、安全，过滤，流控等API管理功能
	 4、提供统一服务出口，解耦
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
	}
}
