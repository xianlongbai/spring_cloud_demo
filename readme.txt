项目结构：
    security-manager    安全管理
    regiest             为eureka注册中心(ha高可用),之后可用consul作为注册中心
    service-api         为服务契约
    service-provider    为服务提供方
    service-consumer    为服务消费方
    zuul-server         网关配置（可用Getway代替）
    config-server       分布式配置文件管理（配合zk+kafka实现动态获取最新配置文件）
    hystrix-monitor     熔断监控（单个服务的监控）
    hystrix-turbine     熔断监控（多个服务同时监控）
    sleuth-zipkin       调用链监控分析
    service-monitor     整体微服务的监控以及服务异常通知（邮件通知）
    boot-demo           做一些springboot相关的功能测试
    
模块组件说明：
    1、Spring Cloud Eureka：
        Eureka负责服务的注册于发现，如果学习过Zookeeper的话，就可以很好的理解，Eureka的角色和 Zookeeper的角色差不多，都是服务的注册和发现，构成
        Eureka体系的包括：服务注册中心、服务提供者、服务消费者。
    2、Spring Cloud Ribbon：
        Eureka只是维护了服务生产者、注册中心、服务消费者三者之间的关系，真正的服务消费者调用服务生产者提供的数据是通过Spring Cloud Ribbon来实现的
        服务消费者是将服务从注册中心获取服务生产者的服务列表并维护在本地的，这种客户端发现模式的方式是服务消费者选择合适的节点进行访问服务生产者提供
        的数据，这种选择合适节点的过程就是Spring Cloud Ribbon完成的，Spring Cloud Ribbon客户端负载均衡器由此而来
    3、Spring Cloud Feign：
        Cloud Feign 是一个声明web服务客户端，这使得编写Web服务客户端更容易，使用Feign 创建一个接口并对它进行注解，它具有可插拔的注解支持包括Feign
        注解与JAX-RS注解，Feign还支持可插拔的编码器与解码器，Spring Cloud增加了对Spring MVC的注解，Spring Web 默认使用了              
        HttpMessageConverters,Spring Cloud 集成 Ribbon 和 Eureka 提供的负载均衡的HTTP客户端 Feign。
        简单的可以理解为：Spring Cloud Feign 的出现使得Eureka和Ribbon的使用更为简单。
    4、Spring Cloud Hystrix：
        hystrix 是一个专用于服务熔断处理的开源项目，当依赖的服务方出现故障不可用时，hystrix有一个所谓的断路器，
        一但打开，就会直接拦截掉对故障服务的调用，从而防止故障进一步扩大
    5、Spring Cloud Config：
        为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件Spring Cloud Config ，它支持配置
        服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。
        在Cpring Cloud Config 组件中，分两个角色，一是Config Server，二是Config Client。
        Config Server用于配置属性的存储，存储的位置可以为Git仓库、SVN仓库、本地文件等，Config Client用于服务属性的读取。
    6、Spring Cloud Zuul：
        Zuul做为网关层，自身也是一个微服务，跟其它服务Service-1，Service-2, ... Service-N一样，都注册在eureka server上，
        可以相互发现，zuul能感知到哪些服务在线，同时通过配置路由规则（后面会给出示例），可以将请求自动转发到指定的后端微服务上，
        对于一些公用的预处理（比如：权限认证，token合法性校验，灰度验证时部分流量引导之类）,可以放在所谓的过滤器(ZuulFilter)里处理，
        这样后端服务以后新增了服务，zuul层几乎不用修改。
    7、Spring Boot admin:
        基于spring boot auactuator之上的可视化服务监控,对堆、栈、GC、线程、日志、接口响应延迟等一系列的监控
    8、Swagger2：
        配合zuul利用swagger2聚合API文档,便于整体查看各个微服务提供的接口服务及调用方式等

项目启动：
    1、eureka注册中心启动
        本地host配置：
            172.30.105.xxx	regiest01
            172.30.105.xxx	regiest02
            172.30.105.xxx	regiest03
        启动3个实例，将自己注册到另外两个实例中：
          client:
            serviceUrl:
                defaultZone: http://admin:admin@regiest02:10020/eureka/,http://admin:admin@regiest03:10030/eureka/
        安全配置：
            #注册中心安全登录认证
            security:
              basic:
                enabled: true
              user:
                name: admin
                password: admin
    2、config-server启动（配置多个端口，启动多个实例，避免单点问题）
         这里启动的是config服务端，config客户端其实就是各个用到分布式配置的微服务端，如consumer2
         git仓库的配置：
              cloud:
                config:
                  server:
                    git:
                      uri: https://github.com/xianlongbai/springcloud-config
              #        search-paths: config-repo   #指定的是匹配查询的路径名
              #公共仓库不用配置用户信息
              #        username: *****
              #        password: *****
        是否需要权限拉去，默认是true,如果不false就不允许你去拉取配置中心Server更新的内容
            management:
                security:
                    enabled: false
        注意：
            configserver支持读取.properties和.yml文件，具体的资源映射关系如下：
                /{application}/{profile}[/{label}]
                /{application}-{profile}.yml
                /{label}/{application}-{profile}.yml
                /{application}-{profile}.properties
                /{label}/{application}-{profile}.properties
            所以：我们的微服务应用名中最好不要带“-”,以免造成读取错误，而且.yml对中文的支持更友好
        测试：
            http://localhost:18888/application-dev.yml
            http://localhost:18888/application-dev/default.json
        客戶端服務：
            将application.yml改为bootstrap.yml，原因是bootstrap.yml会在application.yml之前加载，为的是提前加载远程配置文件和一些初始化连接

    3、service-provider启动
        配置不同端口，启动多个实例，达到服务的高可用
    4、service-consumer启动
        有多个客户端服务，它们有不同的依赖，如依赖远程配置文件
        consumer1：
        consumer2：zk+kafka+config-server
        consumer3：zk+kafka+config-server
        windows下简单启动：
        zk:配置好zk的环境变量，cmd 输入：zkServer启动
        kafka:启动kafka服务：.\bin\windows\kafka-server-start.bat .\config\server.properties
        注意：
            1、kafka的版本和springcloud版本依赖是否匹配，不匹配启动会报错
            2、如果kafka安装在windows下，会出现服务无法启动，解决办法（kafka在windows平台就是有这个BUG，只能手动删除\kafka-logs里的日志文件    
            重启kafka），所以kafka最好安装在linux下
            3、注意微服务的应用名ApplicationName要和git中的配置文件名保持一致（consumerRibbon ------> consumerRibbon-dev.yml）
            4、当config server中心的配置文件内容有变化，手动调用 localhost:8002/refresh POST 刷新配置
               请求/fresh需要有几点要求：1.加actuator的依赖 2.SpringCloud1.5以上需要设置 management.security.enabled=false
            5、配置好消息总线后，调用http://xxx:port/bus/refresh POST 请求,其它模块依赖此配置的也会更新
               例：localhost:8002/bus/refresh   最后说一下，windows下安装kafka真的很坑！！！
            6、利用消息总线加载远程动态配置文件的服务，需要将application.yml改为bootstrap.yml
    5、hystrix-monitor启动
        熔断器的监控页面访问：http://localhost:8888/hystrix
        监控的服务（例）：http://localhost:8002/hystrix.stream
    6、hystrix-turbine启动
        turbine:
          aggregator:
        # 指定聚合哪些集群，多个使用","分割，默认为default。可使用http://.../turbine.stream?cluster={clusterConfig之一}访问
            cluster-config: default
        # 配置Eureka中的serviceId列表，表明监控哪些服务
          app-config: consumer-feign,consumer-ribbon,consumerRibbon
          cluster-name-expression: new String("default")
        # 1. clusterNameExpression指定集群名称，默认表达式appName；此时：turbine.aggregator.clusterConfig需要配置想要监控的应用名称
        # 2. 当clusterNameExpression: default时，turbine.aggregator.clusterConfig可以不写，因为默认就是default
        # 3. 当clusterNameExpression: metadata['cluster']时，假设想要监控的应用配置了eureka.instance.metadata-map.cluster: ABC，
        #    则需要配置，同时turbine.aggregator.clusterConfig: ABC
         
        熔断器的监控页面访问：http://localhost:9999/hystrix
        监控的服务（例）： http://localhost:9999/turbine.stream
        
    7、sleuth-zipkin启动
        服务调用链监控页面访问：localhost:8110/
        微服务端配置：
            zipkin:
                base-url: http://localhost:8110
                #通过配置spring.sleuth.sampler.percentage=0.1这个参数来决定了日志记录发送给采集器的概率，0-1交给使用者自己配置。
                #开发阶段和运行初期，一般配置成1全量收集日志,到了上线后可以慢慢降低这一概率。
                sleuth:
                    sampler:
                        percentage: 1
            例：http://localhost:8002/miya
        注意：调用链监控注意各服务base-url: http://localhost:8110的配置，ip配置错误会导致监控丢失或错误
            
    8、zuul-server启动（后期可以用Getway来代替）
        配置如下：
        #上面这段配置表示，/api-user/开头的url请求，将转发到service-provider这个微服务上，
        #/api-order/开头的url请求，将转发到service-consumer这个微服务上。
        #其实zuul实现负载均衡很简单，使用serviceId进行绑定后，如果有多个相同的serviceid，
        #则会进行轮询的方式进行访问。这个在下文会有具体的结果截图。
        zuul:
          routes:
            api-a:
              path: /api-a/**
              service-id: consumer-feign
              sensitive-headers:
            api-b:
              path: /api-b/**
              service-id: consumer-ribbon
        调用配置为降级服务的接口时，降级调用会有异常，待解决

     9、service-monitor启动（服务监控server端）
       @EnableAdminServer  开启注解
       其它服务均作为client端,需要被监控的加入spring-boot-admin-starter-client依赖即可
       邮件服务依赖：spring-boot-starter-mail
       相关配置：
           #邮件服务(bwprdikmarsobiij)
           # SMTP服务器地址
           spring.mail.host=smtp.qq.com
           # SMTP服务器端口号 默认-1
           # spring.mail.port=-1
           # 发送方帐号
           spring.mail.username=16050973@qq.com
           # 发送方密码（授权码）
           spring.mail.password=xxx
           #javaMailProperties 配置
           # 开启用户身份验证
           spring.mail.properties.mail.smtp.auth=true
           # STARTTLS：一种通信协议，具体可以搜索下
           spring.mail.properties.mail.smtp.starttls.enable=true
           spring.mail.properties.mail.smtp.starttls.required=true
           # 服务重启发送邮件
           spring.boot.admin.notify.mail.enabled=true
           spring.boot.admin.notify.mail.from=16050973@qq.com
           spring.boot.admin.notify.mail.to=xxx@qq.com
     10、Swagger2整合：
        核心就是：处理方式就是在zuul所在的微服务中自定义resourceProvide类实现SwaggerResourceProvider接口，然后我们只要重写get方法
        主要是get方法，代码主要把增加了各个系统的swaggerResource（数据访问来源），
            SwaggerResource有三个参数，
            第一个参数：名称，也就是之前那个下拉框的选择条名称
            第二个参数：url，就是访问具体系统swagger的链接
            第三个参数：version ，就是swagger的版本
            之后就启动网关项目，访问网关项目的swagger地址就可以看到各个系统集中的api数据了
        注意的一点：自定义的一些网关过滤器需要注意一下,比如访问接口必须要带token,会导致在http://127.0.0.1:8077/swagger-ui.html
        页面无法跳转到其它服务的api文档查看页面

 	    
                
    
    

