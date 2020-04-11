package org.lx.framework.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;

/**
 * Netty通道相关操作封装
 */
public class ChannelUtil {

    public static String getChannelIP(Channel channel) {
        return ((InetSocketAddress)channel.remoteAddress()).getAddress().toString().substring(1);
    }

    public static ChannelFuture closeChannel(Channel channel) {
        return channel.close();
    }

    public static ChannelFuture closeChannel(ChannelHandlerContext ctx) {
        return closeChannel(ctx.channel());
    }

    public static ChannelFuture writeAndFlush(Channel channel, Object msg) {
        return channel.writeAndFlush(msg);
    }

}
