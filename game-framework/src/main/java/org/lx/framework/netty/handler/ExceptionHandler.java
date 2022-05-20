package org.lx.framework.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.lx.framework.message.ErrorMessage;

/**
 * @author tanjl
 * @date 2022/5/20 11:50
 */
public class ExceptionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.writeAndFlush(new ErrorMessage(500, "Protocol error"));
    }

}
