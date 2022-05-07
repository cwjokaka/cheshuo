package org.lx.framework.net.channel;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public enum ChannelLifeCycleLink implements ChannelLifeCycle {
    // 单例
    INSTANCE;

    private final List<ChannelLifeCycle> link = new LinkedList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelLifeCycleLink.class);

    @Override
    public void onChannelActive(ChannelHandlerContext ctx) {
        link.forEach(cycle -> {
            try {
                cycle.onChannelActive(ctx);
            } catch (Throwable t) {
                LOGGER.warn("A ChannelLifeCycle#onChannelActive raised an exception. cycle:{} exception:{}",
                        cycle.getClass().getSimpleName(),
                        t.toString());
            }
        });
    }

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {
        link.forEach(cycle -> {
            try {
                cycle.onChannelInactive(ctx);
            } catch (Throwable t) {
                LOGGER.warn("A ChannelLifeCycle#onChannelInactive raised an exception. cycle:{} exception:{}",
                        cycle.getClass().getSimpleName(),
                        t.toString());
            }
        });
    }

    public void addChannelLiftCycle(ChannelLifeCycle cycle) {
        link.add(cycle);
    }

}
