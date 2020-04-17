package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.lx.framework.codec.ProtobufDecoder;
import org.lx.framework.message.MessageRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class TcpInHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketInHandler.class);

    private final MessageRouter messageRouter;

    private final ProtobufDecoder protobufDecoder;

    public TcpInHandler(MessageRouter messageRouter, ProtobufDecoder protobufDecoder) {
        this.messageRouter = messageRouter;
        this.protobufDecoder = protobufDecoder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
//        ByteBuf in = msg.content();
//        short total_len = in.readShort();
//        short module = in.readShort();
//        byte cmd = in.readByte();
//        int size = in.readableBytes();
//        byte[] bytes = new byte[size];
//        in.readBytes(bytes);
//        return protobufDecoder.decode(module, cmd, bytes);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}
