package org.lx.framework.net.channel;

import io.netty.channel.ChannelHandlerContext;

/**
 * Channel生命周期
 * @author LENOVO
 */
public interface ChannelLifeCycle {

    /**
     * 连接建立成功时调用
     */
    void onChannelActive(ChannelHandlerContext ctx);

    /**
     * 连接断开时调用
     */
    void onChannelInactive(ChannelHandlerContext ctx);

}
