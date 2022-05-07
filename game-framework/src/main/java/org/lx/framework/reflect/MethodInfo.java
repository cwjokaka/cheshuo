package org.lx.framework.reflect;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * 方法体数据的封装
 * @author cwjokaka
 */
@Getter
public class MethodInfo {

    /**
     * 方法调用者
     */
    private final Object handler;
    /**
     * 方法自身
     */
    private final Method method;
    /**
     * 方法的各个形参类型
     */
    private final Class<?>[] paramTypes;

    public MethodInfo(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
        this.paramTypes = method.getParameterTypes();
    }

}
