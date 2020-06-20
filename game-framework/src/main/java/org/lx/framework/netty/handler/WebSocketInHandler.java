package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import org.lx.framework.codec.ProtobufDecoder;
import org.lx.framework.enums.ChannelType;
import org.lx.framework.message.Message;
import org.lx.framework.message.MessageRouter;
import org.lx.framework.net.channel.ChannelLifeCycleLink;
import org.lx.framework.net.session.Session;
import org.lx.framework.net.session.SessionManager;
import org.lx.framework.util.ChannelUtil;
import org.lx.framework.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WebSocketInHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketInHandler.class);

    private final MessageRouter messageRouter;

    private final ProtobufDecoder protobufDecoder;

    public WebSocketInHandler(MessageRouter messageRouter, ProtobufDecoder protobufDecoder) {
        this.messageRouter = messageRouter;
        this.protobufDecoder = protobufDecoder;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        LOGGER.info("websock收到消息:{}", msg);
        Message message = protobufDecoder.decode(msg.content());
        Session session = SessionUtil.getSessionFromChannel(ctx);
        messageRouter.route(message, session);
    }

    /**
     * Websocket连接建立时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ChannelLifeCycleLink.INSTANCE.onChannelActive(ctx);
    }


    /**
     * Webscoket连接断开时触发
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ChannelLifeCycleLink.INSTANCE.onChannelInactive(ctx);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("有异常:", cause);
    }

}
