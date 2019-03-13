egister-center 即 eureka 注册中心
service-api 为服务契约
service-consumer 为服务消费方
service-provider 为服务提供方


detail:
1、Spring Cloud Eureka：
    Eureka负责服务的注册于发现，如果学习过Zookeeper的话，就可以很好的理解，Eureka的角色和 Zookeeper的角色差不多，
    都是服务的注册和发现，构成Eureka体系的包括：服务注册中心、服务提供者、服务消费者。
2、Spring Cloud Ribbon：
    Eureka只是维护了服务生产者、注册中心、服务消费者三者之间的关系，真正的服务消费者调用服务生产者提供的数据是通过Spring Cloud Ribbon来实现的
    服务消费者是将服务从注册中心获取服务生产者的服务列表并维护在本地的，这种客户端发现模式的方式是服务消费者选择合适的节点进行访问服务生产者提供的数据，
    这种选择合适节点的过程就是Spring Cloud Ribbon完成的，Spring Cloud Ribbon客户端负载均衡器由此而来
3、Spring Cloud Feign：
    Cloud Feign 是一个声明web服务客户端，这使得编写Web服务客户端更容易，使用Feign 创建一个接口并对它进行注解，
    它具有可插拔的注解支持包括Feign注解与JAX-RS注解，Feign还支持可插拔的编码器与解码器，
    Spring Cloud增加了对Spring MVC的注解，Spring Web 默认使用了HttpMessageConverters,
    Spring Cloud 集成 Ribbon 和 Eureka 提供的负载均衡的HTTP客户端 Feign。
    简单的可以理解为：Spring Cloud Feign 的出现使得Eureka和Ribbon的使用更为简单。
4、Spring Cloud Hystrix：
    hystrix 是一个专用于服务熔断处理的开源项目，当依赖的服务方出现故障不可用时，hystrix有一个所谓的断路器，
    一但打开，就会直接拦截掉对故障服务的调用，从而防止故障进一步扩大
5、Spring Cloud Config：TODO ...
    为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，
    有分布式配置中心组件Spring Cloud Config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。
    在Cpring Cloud Config 组件中，分两个角色，一是Config Server，二是Config Client。
    Config Server用于配置属性的存储，存储的位置可以为Git仓库、SVN仓库、本地文件等，Config Client用于服务属性的读取。
6、Spring Cloud Zuul：
    Zuul做为网关层，自身也是一个微服务，跟其它服务Service-1，Service-2, ... Service-N一样，都注册在eureka server上，
    可以相互发现，zuul能感知到哪些服务在线，同时通过配置路由规则（后面会给出示例），可以将请求自动转发到指定的后端微服务上，
    对于一些公用的预处理（比如：权限认证，token合法性校验，灰度验证时部分流量引导之类）,可以放在所谓的过滤器(ZuulFilter)里处理，
    这样后端服务以后新增了服务，zuul层几乎不用修改。