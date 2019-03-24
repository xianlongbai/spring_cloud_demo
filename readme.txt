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
    
模块组件说明：
    1、Spring Cloud Eureka：
        Eureka负责服务的注册于发现，如果学习过Zookeeper的话，就可以很好的理解，Eureka的角色和 Zookeeper的角色差不多，都是服务的注册和发现，构成           Eureka体系的包括：服务注册中心、服务提供者、服务消费者。
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
         
    2、service-provider启动
        配置不同端口，启动多个实例
    3、service-consumer启动
        有多个客户端服务，它们有不同的依赖，如依赖远程配置文件
        consumer1：
        consumer2：zk+kafka
        consumer3：zk+kafka
        windows下简单启动：
        zk:配置好zk的环境变量，cmd 输入：zkServer启动
        kafka:启动kafka服务：.\bin\windows\kafka-server-start.bat .\config\server.properties
        注意：
            kafka的版本和springcloud版本依赖是否匹配，不匹配启动会报错
            如果kafka安装在windows下，会出现服务无法启动，解决办法（kafka在windows平台就是有这个BUG，只能手动删除\kafka-logs里的日志文件         
            重启kafka）
                
    
    

