package org.lx.cs.server.game.user;

import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.server.game.user.entity.User;
import org.lx.framework.net.channel.AbstractChannelLifeCycle;
import org.lx.framework.net.channel.ChannelLifeCycle;
import org.lx.framework.net.session.Session;
import org.lx.framework.util.SessionUtil;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 用户管理器, 用于管理已登录的用户
 */
@Component
public class UserManager extends AbstractChannelLifeCycle {

    // 保存已登录的用户与session的关联 key: Session  value: User
    private final Map<Session, User> session2user = new ConcurrentHashMap<>();
    // 保存已登录的用户 key: userId  value: User
    private final Map<String, User> onlines = new ConcurrentHashMap<>();

    private final Map<String, Long> uid2sid = new ConcurrentHashMap<>();

    @Override
    public void onChannelInactive(ChannelHandlerContext ctx) {
        Session session = SessionUtil.getSessionFromChannel(ctx);
        User user = session2user.remove(session);
        if (user != null) {
            onlines.remove(user.getId());
            uid2sid.remove(user.getId());
        }
    }

    public Set<Session> getAllOnlineSessions() {
        return new HashSet<>(session2user.keySet());
    }

    public Set<User> getAllOnlineUsers() {
        return new HashSet<>(session2user.values());
    }

    public User getOnlineUser(String uid) {
        return onlines.get(uid);
    }

    public Long getOnlineSessionId(String uid) {
        return uid2sid.get(uid);
    }

    public void addOnlineUser(Session session, User user) {
        session2user.put(session, user);
        onlines.put(user.getId(), user);
        uid2sid.put(user.getId(), session.getSessionId());
    }

}
