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
 * @author LENOVO
 */
@Component
public class SessionManager extends AbstractChannelLifeCycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionManager.class);

    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private static final Map<Long, Session> SESSION_MAP = new ConcurrentHashMap<>();

    @Override
    public void onChannelActive(ChannelHandlerContext ctx) {
        LOGGER.debug("通道激活:{}, 创建Session...", ctx.channel());
        Session session = createSession();
        session.setChannel(ctx.channel())
                .setIp(ChannelUtil.getChannelIP(ctx.channel()));
        if (!SessionUtil.addSessionToChannel(ctx.channel(), session)) {
            LOGGER.debug("session重复添加, 关闭此通道, ip:{}", session.getIp());
            ChannelUtil.closeChannel(ctx);
        }
    }

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {
        LOGGER.debug("通道关闭:{}, 删除Session...", ctx.channel());
        SESSION_MAP.remove(SessionUtil.getSessionFromChannel(ctx).getSessionId());
    }

    public Session createSession() {
        long sid = ID_GENERATOR.addAndGet(1);
        Session session = new Session(sid);
        SESSION_MAP.put(sid, session);
        return session;
    }

    public Session getSession(Long sid) {
        if (sid == null) {
            return null;
        }
        return SESSION_MAP.get(sid);
    }


}
