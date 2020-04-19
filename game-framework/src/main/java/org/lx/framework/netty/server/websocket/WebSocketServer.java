package org.lx.framework.netty.server.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.lx.framework.netty.handler.WebSocketInHandler;
import org.lx.framework.netty.handler.WebSocketOutHandler;
import org.lx.framework.netty.server.IServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * WebSocket协议服务器
 */
@Component("webSocketServer")
public class WebSocketServer implements IServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketServer.class);

    @Value("${game_server.ip}")
    private String ip;
    @Value("${game_server.websocket_port}")
    private int port;

    private final WebSocketInHandler webSocketInHandler;
    private final WebSocketOutHandler webSocketOutHandler;

    public WebSocketServer(WebSocketInHandler webSocketInHandler, WebSocketOutHandler webSocketOutHandler) {
        this.webSocketInHandler = webSocketInHandler;
        this.webSocketOutHandler = webSocketOutHandler;
    }

    @Override
    public void start() {
        LOGGER.info("开启WebSocket服务器...");
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // Http编解码器
                            pipeline.addLast("httpServerCodec", new HttpServerCodec());
                            // ChunkedWriteHandler分块写处理，文件过大会将内存撑爆
                            pipeline.addLast("chunkedWriteHandler", new ChunkedWriteHandler());
                            pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(65536));
                            // WebSocket处理器(自动处理的WebsocketFrame: Ping/Pong/Close)
                            pipeline.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/ws"));
                            pipeline.addLast("webSocketOutHandler", webSocketOutHandler);
                            pipeline.addLast("webSocketInHandler", webSocketInHandler);
                        }
                    });
            LOGGER.info("绑定ip:{}, port:{}", ip, port);
            ChannelFuture future = boot.bind(port).sync();
//            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("start方法异常:{}", e.getMessage());
            e.printStackTrace();
        } finally {
//            LOGGER.info("关闭WebSocketSocket事件循环组...");
//            boss.shutdownGracefully();
//            worker.shutdownGracefully();
        }
    }

}
