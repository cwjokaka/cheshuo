package org.lx.cs.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.lx.cs.server.game.gm.message.GmMessage;
import org.lx.framework.codec.ProtobufEncoder;

public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //使用客户端发送10条数据 hello,server 编号
        System.out.println("发送数据中...");
        ByteBuf buffer = Unpooled.buffer();

        ProtobufEncoder encoder = new ProtobufEncoder();
        byte[] message = encoder.encode(new GmMessage());
        // total_len
        buffer.writeShort(2 + 1 + 1 + message.length);
        // module
        buffer.writeShort(0);
        // cmd
        buffer.writeByte(0);
        buffer.writeBytes(message);
        ctx.writeAndFlush(buffer);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
//        byte[] buffer = new byte[msg.readableBytes()];
//        msg.readBytes(buffer);
//
//        String message = new String(buffer, Charset.forName("utf-8"));
//        System.out.println("客户端接收到消息=" + message);
//        System.out.println("客户端接收消息数量=" + (++this.count));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
