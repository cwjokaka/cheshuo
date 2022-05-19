package org.lx.framework.autoconfigure;

import org.lx.framework.ServerBootstrap;
import org.lx.framework.netty.handler.*;
import org.lx.framework.netty.server.IServer;
import org.lx.framework.netty.server.tcp.TcpSocketServer;
import org.lx.framework.netty.server.websocket.WebSocketServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties(AutoConfigurationProperties.class)
@ConditionalOnClass(ServerBootstrap.class)
@ComponentScan(value = "org.lx.framework")
public class AutoConfigurationClass {

    private AutoConfigurationProperties autoConfigurationProperties;

    private final TcpInHandler tcpInHandler;
    private final WebSocketInHandler webSocketInHandler;
    private final WebSocketOutHandler webSocketOutHandler;
    private final ProtobufEncodeHandler protobufEncodeHandler;
    private final ProtobufDecodeHandler protobufDecodeHandler;

    public AutoConfigurationClass(AutoConfigurationProperties autoConfigurationProperties, TcpInHandler tcpInHandler, WebSocketInHandler webSocketInHandler, WebSocketOutHandler webSocketOutHandler, ProtobufEncodeHandler protobufEncodeHandler, ProtobufDecodeHandler protobufDecodeHandler) {
        this.autoConfigurationProperties = autoConfigurationProperties;
        this.tcpInHandler = tcpInHandler;
        this.webSocketInHandler = webSocketInHandler;
        this.webSocketOutHandler = webSocketOutHandler;
        this.protobufEncodeHandler = protobufEncodeHandler;
        this.protobufDecodeHandler = protobufDecodeHandler;
    }

    @Bean
    public ServerBootstrap serverBootstrap(Map<String, IServer> serverList) {
        return new ServerBootstrap(serverList);
    }

    @ConditionalOnProperty(prefix="cheshuo.server.tcp",name = "enable", havingValue = "true")
    @Bean
    public IServer tcpSocketServer() {
        return new TcpSocketServer(
                autoConfigurationProperties.getHost(),
                autoConfigurationProperties.getServer().getTcp().getPort(),
                tcpInHandler,
                protobufEncodeHandler,
                protobufDecodeHandler);
    }

    @ConditionalOnProperty(prefix="cheshuo.server.websocket",name = "enable", havingValue = "true", matchIfMissing = true)
    @Bean
    public IServer websocketServer() {
        return new WebSocketServer(
                autoConfigurationProperties.getHost(),
                autoConfigurationProperties.getServer().getWebsocket().getPort(),
                webSocketInHandler,
                webSocketOutHandler,
                protobufEncodeHandler
        );
    }



}
