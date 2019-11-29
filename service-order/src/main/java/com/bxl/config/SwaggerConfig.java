package com.bxl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2019/3/31.
 * 在线文档地址 ： http://localhost:9041/swagger-ui.html
 */

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    //是否开启swagger，正式环境一般是需要关闭的，可根据springboot的多环境配置进行设置
//    @Value(value = "${swagger.enabled}")
//    Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar1 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar1.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").defaultValue("admin").build();
        pars.add(tokenPar1.build());
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                // 是否开启
                .enable(true).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage("com.bxl.controller"))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringBoot-Swagger2集成和使用")
                .description("bxl | 分享")
                // 作者信息
                .contact(new Contact("xianlong.bai", "https://github.com/xianlongbai", "16050973@qq.com"))
                .version("1.0.0")
                .build();
    }
}
