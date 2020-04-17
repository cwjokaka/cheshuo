//package org.lx.framework.netty.server.tcp;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import org.lx.framework.netty.server.IServer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * TCP协议服务器
// */
//@Component("tcpSocketServer")
//public class TcpSocketServer implements IServer {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(TcpSocketServer.class);
//
//    @Value("${game_server.ip}")
//    private String ip;
//    @Value("${game_server.tcp_port}")
//    private int port;
//
//    public TcpSocketServer(CommonThreadPool commonThreadPool, Router router) {
//        this.commonThreadPool = commonThreadPool;
//        this.router = router;
//    }
//
//    public void start() {
//        LOGGER.info("开启Socket服务器...");
//        EventLoopGroup boss = new NioEventLoopGroup(1);
//        EventLoopGroup worker = new NioEventLoopGroup();
//        try {
//            ServerBootstrap boot = new ServerBootstrap();
//            boot.group(boss, worker)
//                    .channel(NioServerSocketChannel.class)
//                    // 禁用Nagle算法,使消息立即发出去，不用等待到一定的数据量才发出去, 减少延时
//                    .childOption(ChannelOption.TCP_NODELAY, true)
//                    // 保持长连接
//                    .childOption(ChannelOption.SO_KEEPALIVE, true)
//                    .childHandler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ChannelPipeline pipeline = ch.pipeline();
////                            pipeline.addLast(new )
////                            pipeline.addLast(new MessageDecoder());
////                            pipeline.addLast(new MessageEncoder());
////                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
//                            //pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
//                            // 自定义出入站
////                            pipeline.addLast(new TcpInHandler(commonThreadPool, router));
//                        }
//                    });
//            LOGGER.info("绑定ip:{}, port:{}", ip, port);
//            ChannelFuture future = boot.bind(port).sync();
////            future.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//            LOGGER.error("start方法异常:{}", e.getMessage());
//            e.printStackTrace();
//        } finally {
////            LOGGER.info("关闭TcpSocketServer事件循环组...");
////            boss.shutdownGracefully();
////            worker.shutdownGracefully();
//        }
//    }
//
//}
