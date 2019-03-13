package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;


/**
 * 启动方式：
 * 	localhost:8110/
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableZipkinServer
public class SleuthZipkinApplication {

	public static void main(String[] args) {
		SpringApplication.run(SleuthZipkinApplication.class, args);
	}

}
