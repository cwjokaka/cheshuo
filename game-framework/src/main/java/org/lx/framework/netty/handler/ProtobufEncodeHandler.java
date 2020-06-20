package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import org.lx.framework.codec.ProtobufEncoder;
import org.lx.framework.message.Message;
import org.springframework.stereotype.Component;

/**
 * 公共编码器
 */
@Component
@ChannelHandler.Sharable
public class ProtobufEncodeHandler extends ChannelOutboundHandlerAdapter {

    private final ProtobufEncoder protobufEncoder;

    public ProtobufEncodeHandler(ProtobufEncoder protobufEncoder) {
        this.protobufEncoder = protobufEncoder;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof Message) {
            // 组成私有协议
            ByteBuf output = encode(ctx, (Message) msg);
            ctx.writeAndFlush(output);
            output.release();
            promise.setSuccess();
        } else {
            super.write(ctx, msg, promise);
        }
    }

    private ByteBuf encode(ChannelHandlerContext ctx, Message message) {
        short module = message.getModule();
        byte cmd = message.getCmd();
        ByteBuf output = ctx.alloc().buffer();
//        ByteBuf output = Unpooled.buffer();
        byte[] paramBody = protobufEncoder.encode(message);
//         total_len = module(short) + cmd(byte) + paramBody.length
        output.writeShort(2 + 1 + paramBody.length);
        output.writeShort(module);
        output.writeByte(cmd);
        output.writeBytes(paramBody);
        return output;
    }


}
