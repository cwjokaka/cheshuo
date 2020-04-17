# *cheshuo*
一个轻量级的游戏服务器框架,基于Springboot和Netty,使用Protobuf作为客户端和服务器之间的通信.功能逐步完善中...😘



#### 项目依赖:

| 名称           | 用于                                                 |
| :------------- | ---------------------------------------------------- |
| JDK1.8         | java编译环境                                         |
| Springboot 2.X | 现主要用于依赖注入与简化配置，以及方便日后使用全家桶 |
| Netty 4.X      | NIO通讯框架                                          |
| MongoDB        | 数据持久化                                           |
| jprotobuf      | 简化java protobuf编解码，从此不用手写proto           |
| lombok         | 简化java代码编写                                     |
| Maven          | 项目构建                                             |



#### 模块介绍:

| 模块名            | 说明                               |
| ----------------- | ---------------------------------- |
| game-client-demo  | 实现游戏客户端简单模拟             |
| game-common       | 项目公共模块                       |
| game-framework    | 游戏框架实现                       |
| game-hall         | 游戏大厅(暂时用不上)               |
| game-mb-generator | Mybatis代码生成模块(暂时用不上)    |
| game-server-demo  | 基于game-framework实现的简单服务器 |

