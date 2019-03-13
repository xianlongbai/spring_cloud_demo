package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * 其实zuul实现负载均衡很简单，使用serviceId进行绑定后，如果有多个相同的serviceid，
 * 则会进行轮询的方式进行访问。这个在下文会有具体的结果截图。
 *
 * 例：
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
