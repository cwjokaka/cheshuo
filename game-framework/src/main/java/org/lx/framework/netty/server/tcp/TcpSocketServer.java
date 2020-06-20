package org.lx.framework.netty.server.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.lx.framework.netty.handler.ProtobufEncodeHandler;
import org.lx.framework.netty.handler.TcpInHandler;
import org.lx.framework.netty.server.IServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * TCP协议服务器
 */
@Component("tcpSocketServer")
public class TcpSocketServer implements IServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpSocketServer.class);

    @Value("${game_server.ip}")
    private String ip;
    @Value("${game_server.tcp_port}")
    private int port;

    private TcpInHandler tcpInHandler;

    private ProtobufEncodeHandler protobufEncodeHandler;

    public TcpSocketServer(TcpInHandler tcpInHandler, ProtobufEncodeHandler protobufEncodeHandler) {
        this.tcpInHandler = tcpInHandler;
        this.protobufEncodeHandler = protobufEncodeHandler;
    }

    public void start() {
        LOGGER.info("开启Socket服务器...");
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap boot = new ServerBootstrap();
            boot.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    // 禁用Nagle算法,使消息立即发出去，不用等待到一定的数据量才发出去, 减少延时
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    // 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 解决分包与粘包, 指定传递最大为65536字节，长度字段为2byte(short), 消费这两个byte直接把Message字节传到下一个Handler
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(
                                    65536,
                                    0,
                                    2,
                                    0,
                                    0       // 不消费这两个字节，由decoder统一处理
                                    )
                            );
                            pipeline.addLast(new IdleStateHandler(0, 0, 30, TimeUnit.SECONDS));
                            pipeline.addLast(tcpInHandler);
                            pipeline.addLast(protobufEncodeHandler);
                        }
                    });
            LOGGER.info("绑定ip:{}, port:{}", ip, port);
            ChannelFuture future = boot.bind(port).sync();
//            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("start方法异常:{}", e.getMessage());
            e.printStackTrace();
        } finally {
//            LOGGER.info("关闭TcpSocketServer事件循环组...");
//            boss.shutdownGracefully();
//            worker.shutdownGracefully();
        }
    }

}
