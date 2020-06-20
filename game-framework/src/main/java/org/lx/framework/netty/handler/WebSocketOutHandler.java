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

/**
 * 用于把协议包装成Websocket帧
 */
@Component
@ChannelHandler.Sharable
public class WebSocketOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof String) {
            ctx.writeAndFlush(new TextWebSocketFrame((String) msg));
        } else if (msg instanceof ByteBuf){
            // 组成私有协议
            ChannelUtil.writeAndFlush(ctx, new BinaryWebSocketFrame((ByteBuf)msg));
        } else {
            super.write(ctx, msg, promise);
        }
    }

}
