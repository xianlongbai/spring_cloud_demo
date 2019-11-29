package com.bxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 访问配置文件方式：http://localhost:18888/xxx.yml
 * 注意：出现Dirty files found:[xxx.yml,...] 异常时,需要做一下处理：
 * 		1、增加force-pull: true，本地仓库如果有脏数据，则会强制拉取
 * 		2、configserver启动后，删除本地缓存，即.git文件夹
 */
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class ConfigServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerApplication.class, args);
	}

}
