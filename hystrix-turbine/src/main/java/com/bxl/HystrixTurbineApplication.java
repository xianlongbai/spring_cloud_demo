package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 *
 * 熔断器的监控页面访问
 * 		http://localhost:9999/hystrix
 *	 监控的服务（例）：
 *	    http://localhost:9999/turbine.stream
 *
 */

@SpringBootApplication
@EnableTurbine   //开启turbine，@EnableTurbine注解包含了@EnableDiscoveryClient注解，即开启了注册服务
@EnableHystrixDashboard
public class HystrixTurbineApplication {

	public static void main(String[] args) {
		//SpringApplication.run(HystrixTurbineApplication.class, args);
		new SpringApplicationBuilder(HystrixTurbineApplication.class).web(true).run(args);
	}

}
