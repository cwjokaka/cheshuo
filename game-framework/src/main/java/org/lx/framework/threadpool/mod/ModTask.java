package org.lx.framework.threadpool.mod;

import org.lx.framework.reflect.MethodInfo;
import org.lx.framework.threadpool.AbsTask;

import java.lang.reflect.Method;

public class ModTask extends AbsTask {

    private long sessionId;

    public ModTask(Object handler, Method method, Object[] params, long sessionId) {
        super(handler, method, params);
        this.sessionId = sessionId;
    }

    public long getSessionId() {
        return sessionId;
    }

}
