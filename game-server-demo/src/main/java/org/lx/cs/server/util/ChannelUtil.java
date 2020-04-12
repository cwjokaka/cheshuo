package org.lx.cs.server.util;

import io.netty.channel.Channel;

import java.net.InetSocketAddress;

public class ChannelUtil {

    public static String getChannelIP(Channel channel) {
        return ((InetSocketAddress)channel.remoteAddress()).getAddress().toString().substring(1);
    }

}
