package tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 用于与TcpSocketServer通信
 */
public class TcpClient {

    private Channel channel;
    private Channel agentChannel;

    public TcpClient(Channel agentChannel) {
        this.agentChannel = agentChannel;
    }

    public void start() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        TcpClient self = this;
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new TcpClientInHandler(agentChannel));
                            pipeline.addLast(new TcpClientOutHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8400).sync();
            this.channel = channelFuture.channel();
            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void sendMessage(byte[] bytes) {
        System.out.println("TcpClient:写消息给Tcp服务端:" + channel.remoteAddress().toString()+"..." + new String(bytes));
        channel.writeAndFlush(Unpooled.copiedBuffer(bytes));
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }
}
