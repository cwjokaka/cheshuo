package org.lx.framework.util;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.lx.framework.net.session.Session;

/**
 * session相关操作封装
 */
public class SessionUtil {

    private static final AttributeKey<Session> SESSION_ATTRIBUTE_KEY = AttributeKey.valueOf("lx_session");

    /**
     * 添加session到channel_attr, CAS保证线程安全
     * @param channel netty channel
     * @param session {@link Session}
     * @return boolean
     */
    public static boolean addSessionToChannel(Channel channel, Session session) {
        Attribute<Session> sessionAttr = channel.attr(SESSION_ATTRIBUTE_KEY);
        return sessionAttr.compareAndSet(null, session);
    }

    public static Session getSessionFromChannel(Channel channel) {
        Attribute<Session> sessionAttr = channel.attr(SESSION_ATTRIBUTE_KEY);
        return sessionAttr.get();
    }
    public static Session getSessionFromChannel(ChannelHandlerContext ctx) {
        return getSessionFromChannel(ctx.channel());
    }

    public static void removeSessionFromChannel(Channel channel) {
        Attribute<Session> sessionAttr = channel.attr(SESSION_ATTRIBUTE_KEY);
        sessionAttr.set(null);
    }

    public static void removeSessionFromChannel(ChannelHandlerContext ctx) {
        removeSessionFromChannel(ctx.channel());
    }

}
