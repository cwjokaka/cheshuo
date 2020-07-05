# *cheshuo*
一个轻量级的游戏服务器框架,基于Springboot和Netty,使用Protobuf作为客户端和服务器之间的通信.功能逐步完善中...😘

------



## 目前支持协议: 

- TCP
- Websocket

  


## 项目依赖:

| 名称           | 用于                                          | 链接                                   |
| :------------- | --------------------------------------------- | -------------------------------------- |
| JDK1.8         | Java环境                                      |                                        |
| Springboot 2.X | 主要使用IOC与简化配置，以及方便日后使用全家桶 | https://spring.io/projects/spring-boot |
| Netty 4.X      | NIO通讯框架                                   | https://netty.io/                      |
| jprotobuf      | 简化java protobuf编解码                       | https://github.com/jhunters/jprotobuf  |
| Maven          | 项目构建                                      |                                        |



## 模块介绍:

| 模块名            | 说明                               |
| ----------------- | ---------------------------------- |
| game-client-demo  | H5界面的游戏客户端模拟, 使用TCP协议时需开启TcpAgent把ws转为TCP |
| game-framework    | 游戏服务框架的实现                   |
| game-server-demo  | 基于game-framework实现的简单服务器demo |



## 快速开始:

1.添加依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>cheshuo-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

2.参考配置application.yml

```yaml
cheshuo:
  host: localhost		# 服务地址, 默认0.0.0.0
  server:
    tcp:
      enable: true		# 开启TCP服务, 默认false
      port: 8400		# TCP服务端口, 默认8400
    websocket:
      enable: true		# 开启Websocket服务, 默认true
      port: 8500		# Websocket服务端口, 默认8500
```

3.编写请求&响应体

```java
@MessageMeta(module = Modules.USER, cmd = UserCmd.LOGIN_REQ)
public class LoginReq extends Message {
    private String account;
    private String password;
	// 省略getter & setter...
}
```

```java
@MessageMeta(module = Modules.USER, cmd = UserCmd.LOGIN_RESP)
public class LoginResp extends Message {
	private Integer code;
	// 省略getter & setter...
}

```

4.注册Handler

```java
// module类型为short, 表示业务模块编号
@Handler(module = Modules.USER)
public class UserHandler {
    
    // 业务处理类
    private final UserService userService;
    
    // 省略getter & setter...
    
    // cmd类型为short, 表示具体业务
    @Mapping(cmd = UserCmd.LOGIN_REQ)
    public LoginResp login(Session session, LoginReq loginReq) {
        return userService.login(session, loginReq);
    }
}

```

5.引导启动

```java
import org.lx.framework.ServerBootstrap;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private ServerBootstrap serverBootstrap;

    public Application(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        serverBootstrap.startAll();
    }

}
```

## License

Open source based on **MIT**