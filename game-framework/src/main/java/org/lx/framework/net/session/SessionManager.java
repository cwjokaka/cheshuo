package org.lx.framework.net.session;

import io.netty.channel.ChannelHandlerContext;
import org.lx.framework.enums.ChannelType;
import org.lx.framework.net.channel.AbstractChannelLifeCycle;
import org.lx.framework.net.channel.ChannelLifeCycle;
import org.lx.framework.util.ChannelUtil;
import org.lx.framework.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Session管理器
 */
@Component
public class SessionManager extends AbstractChannelLifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

    private static final AtomicLong idGenerator = new AtomicLong(0);
    private static final Map<Long, Session> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void onChannelActive(ChannelHandlerContext ctx) {
        LOGGER.info("通道激活:{}, 创建Session...", ctx.channel());
        Session session = createSession();
        session.setChannel(ctx.channel())
                .setChannelType(ChannelType.WEBSOCKET)
                .setIp(ChannelUtil.getChannelIP(ctx.channel()));
        if (!SessionUtil.addSessionToChannel(ctx.channel(), session)) {
            LOGGER.warn("session重复添加, 关闭此通道, ip:{}", session.getIp());
            ChannelUtil.closeChannel(ctx);
        }
    }

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {
        LOGGER.info("通道关闭:{}, 删除Session...", ctx.channel());
        sessionMap.remove(SessionUtil.getSessionFromChannel(ctx).getSessionId());
    }

    public Session createSession() {
        long sid = idGenerator.addAndGet(1);
        Session session = new Session(sid);
        sessionMap.put(sid, session);
        return session;
    }

    public Session getSession(Long sid) {
        if (sid == null) {
            return null;
        }
        return sessionMap.get(sid);
    }


}
