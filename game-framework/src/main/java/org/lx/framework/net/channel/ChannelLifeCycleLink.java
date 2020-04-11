package org.lx.framework.net.channel;

import io.netty.channel.ChannelHandlerContext;

import java.util.LinkedList;
import java.util.List;

public enum ChannelLifeCycleLink implements ChannelLifeCycle {

    INSTANCE;

    private final List<ChannelLifeCycle> link = new LinkedList<>();

    @Override
    public void onChannelActive(ChannelHandlerContext ctx) {
        link.forEach(cycle -> {
            cycle.onChannelActive(ctx);
        });
    }

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {
        link.forEach(cycle -> {
            cycle.onChannelInactive(ctx);
        });
    }

    public void addChannelLiftCycle(ChannelLifeCycle cycle) {
        link.add(cycle);
    }

}
