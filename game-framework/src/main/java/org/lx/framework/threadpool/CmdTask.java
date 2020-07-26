package org.lx.framework.threadpool;

import org.lx.framework.net.session.Session;
import org.lx.framework.net.session.SessionManager;
import org.lx.framework.util.ApplicationContextUtil;
import org.lx.framework.util.ChannelUtil;

import java.lang.reflect.Method;
import java.util.Objects;

public class CmdTask extends AbsTask {

    private long sessionId;

    public CmdTask(Object handler, Method method, Object[] params, long sessionId) {
        super(handler, method, params);
        this.sessionId = sessionId;
    }

    @Override
    public void afterRun(Object res) {
        SessionManager sm = ApplicationContextUtil.getBean(SessionManager.class);
        Session session = sm.getSession(sessionId);
        if (session != null) {
//            session.getChannel();
            ChannelUtil.writeAndFlush(session.getChannel(), res);
        }
    }

    public long getSessionId() {
        return sessionId;
    }
}
