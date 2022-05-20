package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.lx.framework.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author tanjl
 * @date 2022/5/20 09:33
 */
@Component
@ChannelHandler.Sharable
public class WebSocketContentHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketContentHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOGGER.info("websocket收到消息:{}", msg);
        if (msg instanceof BinaryWebSocketFrame) {
            ByteBuf content = ((BinaryWebSocketFrame) msg).content();
            content.readShort();
            ctx.fireChannelRead(content);
        }

    }

}
