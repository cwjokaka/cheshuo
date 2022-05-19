package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.lx.framework.codec.ProtobufDecoder;
import org.lx.framework.codec.ProtobufEncoder;
import org.lx.framework.message.Message;
import org.lx.framework.message.MessageRouter;
import org.lx.framework.message.heartbeat.HeartBeat;
import org.lx.framework.net.channel.ChannelLifeCycleLink;
import org.lx.framework.net.session.Session;
import org.lx.framework.util.ChannelUtil;
import org.lx.framework.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class TcpInHandler extends SimpleChannelInboundHandler<Message> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpInHandler.class);

    private final MessageRouter messageRouter;

    private final HeartBeat heartBeat = new HeartBeat();

    public TcpInHandler(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg)  {
        LOGGER.info("TcpInHandler收到消息:" + msg);
        Session session = SessionUtil.getSessionFromChannel(ctx);
        messageRouter.route(msg, session);
    }

    /**
     * TCP连接建立时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ChannelLifeCycleLink.INSTANCE.onChannelActive(ctx);
    }

    /**
     * TCP连接断开时触发
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

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 发送心跳消息，并在发送失败时关闭该连接
            ChannelUtil.writeAndFlush(ctx.channel(), heartBeat).addListener(
                    ChannelFutureListener.CLOSE_ON_FAILURE
            );
        } else {
            // 传递事件给下一个inHandler
            super.userEventTriggered(ctx, evt);
        }
    }
}
