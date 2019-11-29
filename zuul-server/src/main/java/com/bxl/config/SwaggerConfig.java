package com.bxl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2019/3/31.
 *
    主要是get方法，代码主要把增加了各个系统的swaggerResource（数据访问来源），
      SwaggerResource有三个参数，
      第一个参数：名称，也就是之前那个下拉框的选择条名称
      第二个参数：url，就是访问具体系统swagger的链接
      第三个参数：version ，就是swagger的版本
      之后就启动网关项目，访问网关项目的swagger地址就可以看到各个系统集中的api数据了
 */

@EnableSwagger2
@Configuration
@Primary //多个bean时 此类优先使用
public class SwaggerConfig implements SwaggerResourcesProvider {

    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
//    @Value(value = "${swagger.enabled}")
//    Boolean swaggerEnabled;

    @Autowired
    RouteLocator routeLocator;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(true).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.bxl.controller"))
                //.apis(RequestHandlerSelectors.basePackage("com.bxl.api"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }

    //设置api信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot-Swagger2集成和使用")
                .description("bxl | 分享")
                // 作者信息
                .contact(new Contact("xianlong.bai", "https://github.com/xianlongbai", "16050973@qq.com"))
                .version("1.0.0")
                .termsOfServiceUrl("https://github.com/xianlongbai/spring_cloud_demo")
                .build();
    }


    @Override
    public List<SwaggerResource> get() {
//方式一：利用routeLocator动态引入微服务
        List<SwaggerResource> resources = new ArrayList<>();
        resources.add(swaggerResource("zuul-server","/v2/api-docs","2.0"));
//        resources.add(swaggerResource("consumer-feign","/consumer-feign/v2/api-docs","2.0"));
//        resources.add(swaggerResource("consumer-ribbon","/api-b/v2/api-docs","2.0"));

//方式二：循环 使用Lambda表达式简化代码
        routeLocator.getRoutes().forEach(route ->{
            //动态获取
            resources.add(swaggerResource(route.getId(),route.getFullPath().replace("**", "v2/api-docs?token=123"), "2.0"));
        });

//方式二：也可以直接 继承 Consumer接口
//      routeLocator.getRoutes().forEach(new Consumer<Route>() {
//
//          @Override
//          public void accept(Route t) {
//              // TODO Auto-generated method stub
//
//          }
//      });
        return resources;
    }

    private SwaggerResource swaggerResource(String name,String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
