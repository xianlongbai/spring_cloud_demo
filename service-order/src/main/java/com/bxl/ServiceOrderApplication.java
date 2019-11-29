package com.bxl;

import com.bxl.config.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 *
 *
 * 特别是@LoadBalanced，经过这个修饰的restTemplate，就不是普通的restTemplate了，
 * 而是具备负载均衡能力的restTemplate，即每次都会用负载均衡算法，从可用服务列表中，挑一个进行调用。
 *
 */

//从Spring Cloud 1.0.0.RC1版本开始，就已经不推荐使用EnableEurekaClient和EnableHystrix了
//@EnableHystrix   //其中包含@EnableCircuitBreaker
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker  //熔断注解
@RibbonClient(name="SERVICE-USER", configuration = MySelfRule.class)
public class ServiceOrderApplication {


	/**
	 * RestTemplate发起一个请求，这个请求被LoadBalancerInterceptor给拦截了，
	 * 拦截后将请求的地址中的服务逻辑名转为具体的服务地址，然后继续执行请求，就是这么一个过程。
	 * Cloud Ribbon实现客户端负载也基本如此
	 * @return
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ServiceOrderApplication.class, args);
	}
}
