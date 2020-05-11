# *cheshuo*
一个轻量级的游戏服务器框架,基于Springboot和Netty,使用Protobuf作为客户端和服务器之间的通信.功能逐步完善中...😘

------



#### 目前支持协议: 

- TCP

- Websocket

  


#### 项目依赖:

| 名称           | 用于                                          | 链接                                      |
| :------------- | --------------------------------------------- | ----------------------------------------- |
| JDK1.8         | Java环境                                      |                                           |
| Springboot 2.X | 主要使用IOC与简化配置，以及方便日后使用全家桶 | https://spring.io/projects/spring-boot    |
| Netty 4.X      | NIO通讯框架                                   | https://netty.io/                         |
| MongoDB        | 服务器demo数据持久化                          | https://www.mongodb.com/                  |
| jprotobuf      | 简化java protobuf编解码                       | https://github.com/jhunters/jprotobuf     |
| protobuf.js    | H5客户端protobuf支持                          | https://github.com/protobufjs/protobuf.js |
| lombok         | 简化java代码编写                              | https://github.com/rzwitserloot/lombok    |
| Maven          | 项目构建                                      |                                           |



#### 模块介绍:

| 模块名            | 说明                               |
| ----------------- | ---------------------------------- |
| game-client-demo  | H5界面的游戏客户端模拟, 使用TCP协议时需开启TcpAgent把ws转为TCP |
| game-common       | 公共模块                       |
| game-framework    | 游戏框架实现                       |
| game-hall         | 游戏大厅(暂时无用)        |
| game-mb-generator | Mybatis代码生成模块, 以插件方式运行 |
| game-server-demo  | 基于game-framework实现的简单服务器demo |

