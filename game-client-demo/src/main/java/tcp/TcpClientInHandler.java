package tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class TcpClientInHandler extends ChannelInboundHandlerAdapter {

    private final Channel agentChannel;

    public TcpClientInHandler(Channel agentChannel) {
        this.agentChannel = agentChannel;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
//        this.tcpClient.setChannel(ctx.channel());
//        System.out.println("TcpClient:建立连接成功");
//        ByteBuf buffer = Unpooled.copiedBuffer("hello,server ", Charset.forName("utf-8"));
//        ctx.writeAndFlush(buffer);
        System.out.println("TcpClientInHandler:建立连接成功, 远程服务器:" + ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("链接断开了...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("TcpClientInHandler:读取到消息");
        agentChannel.writeAndFlush(new BinaryWebSocketFrame((ByteBuf) msg));
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
