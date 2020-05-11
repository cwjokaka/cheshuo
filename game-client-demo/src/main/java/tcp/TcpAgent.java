package tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 *  TCP代理，用于转发由Websocket发来的消息到TCP服务器上
 */
public class TcpAgent {

    private final static int PORT = 13329;

    public static void main(String[] args) {
        new TcpAgent().start();
    }

    public void start() {
        System.out.println("TcpAgent:开启TCP代理服务器...");
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
                            pipeline.addLast("TcpAgentHandler", TcpAgentHandler.getInstance());
                        }
                    });
            System.out.println("TcpAgent:绑定port:" + PORT);
            ChannelFuture future = boot.bind(PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            System.out.println("TcpAgent:start方法异常:" + e.getMessage());
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


}
