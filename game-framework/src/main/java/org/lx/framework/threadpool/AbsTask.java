package org.lx.framework.threadpool;

import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 基本任务的封装,反射方式执行
 */
public abstract class AbsTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbsTask.class);

    private final Object handler;
    private final Method method;
    private final Object[] params;

    public AbsTask(Object handler, Method method, Object[] params) {
        this.handler = handler;
        this.method = method;
        this.params = params;
    }

    @Override
    public void run() {
        Object res;
        try {
            res = method.invoke(handler, params);
            afterRun(res);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("方法执行失败", e);
        }
    }

    public void afterRun(Object res){}

    public Object getHandler() {
        return handler;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getParams() {
        return params;
    }
}
