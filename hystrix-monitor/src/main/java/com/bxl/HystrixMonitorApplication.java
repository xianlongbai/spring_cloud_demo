package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


/**
 *
 * 熔断器的监控页面访问
 * 		http://localhost:8888/hystrix
 *	 监控的服务（例）：
 *	    http://localhost:8002/hystrix.stream
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class HystrixMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixMonitorApplication.class, args);
	}
}
