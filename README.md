# spring-cloud-example
spring cloud 的一些范例

## 概要
对近来使用 spring cloud 做下总结与复盘.

##项目结构
```
spring-cloud-example
  |- eureka-server
  |- hello-server
  |- ribbon-consumer
  |- example-service-api
  |- config-server
  |- config-repo
  |- config-consumer

```
### eureka-server
eureka 注册中心, 使用 `@EnableEurekaServer` 来启动 eureka 服务;  这里配置了两个服务来测试高可用的.

```java
@SpringBootApplication
@EnableEurekaServer
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
```

```yml
---
spring:
  profiles: eureka-server-1

server:
  port: 8761

eureka:
  instance:
    hostname: eureka-server-1
  client:
    service-url:
      defaultZone: http://eureka-server-2:8762/eureka/
  server:
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 6000


---
spring:
  profiles: eureka-server-2
server:
  port: 8762

eureka:
  instance:
    hostname: eureka-server-2
  client:
    service-url:
      defaultZone: http://eureka-server-1:8761/eureka/
  server:
    enable-self-preservation: false
    eviction-interval-timer-in-ms: 6000

```

### hello-server
使用 `@EnableEurekaClient` 开启服务注册查询. 这里实现 了一个简单的 `@RestController` 接口. 配置了两套环境. 

```java
@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}


@RestController
public class HelloController implements HelloApi {
    public static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("hello")
    public String hello(String name) {
        LOGGER.info("say hello to {}", name);
        return new StringBuffer("hello, ").append(name).toString();
    }

    @Override
    public String helloWithFeign(@RequestParam String name) {
        LOGGER.info("say hello to {} with feign", name);
        return new StringBuffer("hello, ").append(name).append(", here is feign.").toString();
    }
}

```

application-hello-server-1.yml/application-hello-server-2.yml; 基本一样把server.prot 改一下就行.
```yml
spring:
  application:
    name: hello-server
server:
  port: 8001
eureka:
  client:
    serviceUrl:
      defaultZone: http://eureka-server-1:8761/eureka/,http://eureka-server-2:8762/eureka/
  instance:
    lease-renewal-interval-in-seconds: 5
    lease-expiration-duration-in-seconds: 10

```

## 运行

### hosts文件里添加如下两行
```
127.0.0.1    eureka-server-1
127.0.0.1    eureka-server-2
```
