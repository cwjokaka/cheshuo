package org.lx.framework.autoconfigure;

import org.lx.framework.ServerBootstrap;
import org.lx.framework.netty.handler.ProtobufEncodeHandler;
import org.lx.framework.netty.handler.TcpInHandler;
import org.lx.framework.netty.handler.WebSocketInHandler;
import org.lx.framework.netty.handler.WebSocketOutHandler;
import org.lx.framework.netty.server.IServer;
import org.lx.framework.netty.server.tcp.TcpSocketServer;
import org.lx.framework.netty.server.websocket.WebSocketServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(AutoConfigurationProperties.class)
@ConditionalOnClass(ServerBootstrap.class)
public class AutoConfigurationClass {

    private AutoConfigurationProperties autoConfigurationProperties;

    private TcpInHandler tcpInHandler;
    private final WebSocketInHandler webSocketInHandler;
    private final WebSocketOutHandler webSocketOutHandler;
    private ProtobufEncodeHandler protobufEncodeHandler;

    public AutoConfigurationClass(AutoConfigurationProperties autoConfigurationProperties, TcpInHandler tcpInHandler, WebSocketInHandler webSocketInHandler, WebSocketOutHandler webSocketOutHandler, ProtobufEncodeHandler protobufEncodeHandler) {
        this.autoConfigurationProperties = autoConfigurationProperties;
        this.tcpInHandler = tcpInHandler;
        this.webSocketInHandler = webSocketInHandler;
        this.webSocketOutHandler = webSocketOutHandler;
        this.protobufEncodeHandler = protobufEncodeHandler;
    }

    @Bean
    public ServerBootstrap serverBootstrap(Map<String, IServer> serverList) {
        return new ServerBootstrap(serverList);
    }

    @ConditionalOnProperty(prefix="cheshuo.server.tcp",name = "enable", havingValue = "true")
    @Bean
    public IServer tcpSocketServer() {
        return new TcpSocketServer(
                autoConfigurationProperties.getIp(),
                autoConfigurationProperties.getServer().getTcp().getPort(),
                tcpInHandler,
                protobufEncodeHandler);
    }

    @ConditionalOnProperty(prefix="cheshuo.server.websocket",name = "enable", havingValue = "true")
    @Bean
    public IServer websocketServer() {
        return new WebSocketServer(
                autoConfigurationProperties.getIp(),
                autoConfigurationProperties.getServer().getWebsocket().getPort(),
                webSocketInHandler,
                webSocketOutHandler,
                protobufEncodeHandler
        );
    }



}