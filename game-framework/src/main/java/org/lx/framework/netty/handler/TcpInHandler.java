package org.lx.framework.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.lx.framework.codec.ProtobufDecoder;
import org.lx.framework.message.Message;
import org.lx.framework.message.MessageRouter;
import org.lx.framework.net.channel.ChannelLifeCycleLink;
import org.lx.framework.net.session.Session;
import org.lx.framework.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class TcpInHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TcpInHandler.class);

    private final MessageRouter messageRouter;

    private final ProtobufDecoder protobufDecoder;

    public TcpInHandler(MessageRouter messageRouter, ProtobufDecoder protobufDecoder) {
        this.messageRouter = messageRouter;
        this.protobufDecoder = protobufDecoder;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        LOGGER.info("TcpInHandler收到消息:" + msg);
        Message message = decode((ByteBuf) msg);
        Session session = SessionUtil.getSessionFromChannel(ctx);
        messageRouter.route(message, session);
    }

    private Message decode(ByteBuf in) {
//        short total_len = in.readShort();
        short module = in.readShort();
        byte cmd = in.readByte();
        int size = in.readableBytes();
        byte[] bytes = new byte[size];
        in.readBytes(bytes);
        return protobufDecoder.decode(module, cmd, bytes);
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

}
