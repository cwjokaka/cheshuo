package tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class TcpAgentHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    // key: agentChannel value: clientChannel
    private final Map<Channel, Channel> distributes = new ConcurrentHashMap<>();
    private final static TcpAgentHandler INSTANCE = new TcpAgentHandler();

    private TcpAgentHandler(){}

    public static TcpAgentHandler getInstance() {
        return INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        System.out.println("TcpAgentHandler:收到消息");
        Channel clientChannel = distributes.get(ctx.channel());
        int length = msg.content().readableBytes();
        byte[] data = new byte[length];
        msg.content().readBytes(data);
        System.out.println("TcpAgentHandler:转发消息给Tcp服务器..." + new String(data));
        clientChannel.writeAndFlush(Unpooled.copiedBuffer(data));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 创建一个Bootstrap类的实例以连接到服务器
        Bootstrap bootstrap = new Bootstrap();
        // 指定Channel的实现
        bootstrap
                // 使用与分配给已被接受的子Channel相同的EventLoop
                .group(ctx.channel().eventLoop())
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new TcpClientInHandler(ctx.channel()));
                    }
                });

        // 连接到远程节点
        ChannelFuture connectFuture = bootstrap.connect(new InetSocketAddress("localhost", 8400));
        connectFuture.addListener((future -> {
            if (future.isSuccess()) {
                System.out.println("连接成功");
                distributes.put(ctx.channel(), connectFuture.channel());
            }
        }));

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接断开");
        distributes.remove(ctx.channel());
    }
}
