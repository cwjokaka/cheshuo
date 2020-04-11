package org.lx.cs.hall;

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
import org.lx.cs.hall.handler.HttpInHandler;
import org.lx.cs.hall.task.CommonThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GameServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    @Value("${slg.ip}")
    private String ip;
    @Value("${slg.port}")
    private int port;

    private final CommonThreadPool commonThreadPool;
    private final Router router;

    public GameServer(CommonThreadPool commonThreadPool, Router router) {
        this.commonThreadPool = commonThreadPool;
        this.router = router;
    }


    public void start() {
        LOGGER.info("开启服务器...");
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
                            pipeline.addLast("HttpServerCodec",new HttpServerCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
//                            pipeline.addLast("http-chunked", new ChunkedWriteHandler());

                            // 自定义出入站
                            pipeline.addLast("HttpInHandler", new HttpInHandler(commonThreadPool, router));
//                            pipeline.addLast("HttpOutHandler", new HttpOutHandler());
                        }
                    });
            LOGGER.info("绑定ip:{}, port:{}", ip, port);
            ChannelFuture future = boot.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.error("start方法异常:{}", e.getMessage());
            e.printStackTrace();
        } finally {
            LOGGER.info("关闭事件循环组...");
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
