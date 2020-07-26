package org.lx.framework.threadpool;

import org.lx.framework.util.ChannelUtil;

import java.lang.reflect.Method;

public class CmdTask extends AbsTask {

    private long sessionId;

    public CmdTask(Object handler, Method method, Object[] params, long sessionId) {
        super(handler, method, params);
        this.sessionId = sessionId;
    }

    @Override
    public void afterRun(Object res) {
        // TODO 获取Session后返回响应给客户端
//        ChannelUtil.writeAndFlush(modTask.getSession().getChannel(), resp);
    }

}
