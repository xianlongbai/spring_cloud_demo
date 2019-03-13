package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Consumer03Application {

	public static void main(String[] args) {
		SpringApplication.run(Consumer03Application.class, args);
	}

}
