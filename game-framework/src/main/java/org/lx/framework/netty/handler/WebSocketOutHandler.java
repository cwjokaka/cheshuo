package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.lx.framework.codec.ProtobufEncoder;
import org.lx.framework.message.Message;
import org.lx.framework.message.MessageManager;
import org.lx.framework.util.ChannelUtil;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WebSocketOutHandler extends ChannelOutboundHandlerAdapter {

    private final ProtobufEncoder protobufEncoder;


    public WebSocketOutHandler(ProtobufEncoder protobufEncoder) {
        this.protobufEncoder = protobufEncoder;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof String) {
            ctx.writeAndFlush(new TextWebSocketFrame((String) msg));
        } else if (msg instanceof ByteBuf){
            // 组成私有协议
//            ByteBuf output = encode((Message) msg);
            ChannelUtil.writeAndFlush(ctx, new BinaryWebSocketFrame((ByteBuf)msg));
        } else {
            super.write(ctx, msg, promise);
        }
    }

    private ByteBuf encode(Message message) {
        short module = message.getModule();
        byte cmd = message.getCmd();
        ByteBuf output = Unpooled.buffer();
        byte[] paramBody = protobufEncoder.encode(message);
        // total_len = module(short) + cmd(byte) + paramBody.length
        output.writeShort(2 + 1 + paramBody.length);
        output.writeShort(module);
        output.writeByte(cmd);
        output.writeBytes(paramBody);
        return output;
    }


}
