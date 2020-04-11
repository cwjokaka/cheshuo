package org.lx.framework.net.channel;

import io.netty.channel.ChannelHandlerContext;

/**
 * Channel生命周期
 */
public interface ChannelLifeCycle {

    void onChannelActive(ChannelHandlerContext ctx);

    void onChannelInactive(ChannelHandlerContext ctx);

}
