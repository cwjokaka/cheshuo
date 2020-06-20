package org.lx.framework.net.channel;

import io.netty.channel.ChannelHandlerContext;

public abstract class AbstractChannelLifeCycle implements ChannelLifeCycle {
    @Override
    public void onChannelActive(ChannelHandlerContext ctx) {}

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {}
}
