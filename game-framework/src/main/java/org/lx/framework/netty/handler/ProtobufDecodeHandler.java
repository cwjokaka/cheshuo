package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.lx.framework.codec.ProtobufDecoder;
import org.lx.framework.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author tanjl
 * @date 2022/5/19 11:36
 */
@Component
@ChannelHandler.Sharable
public class ProtobufDecodeHandler extends SimpleChannelInboundHandler<ByteBuf> {

    private final ProtobufDecoder protobufDecoder;

    public ProtobufDecodeHandler(ProtobufDecoder protobufDecoder) {
        this.protobufDecoder = protobufDecoder;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ctx.fireChannelRead(protobufDecoder.decode(msg));
    }

}
