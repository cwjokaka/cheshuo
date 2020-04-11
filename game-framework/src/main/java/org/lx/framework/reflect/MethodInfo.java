package org.lx.framework.reflect;

import lombok.Getter;

import java.lang.reflect.Method;

/**
 * 方法体数据的封装
 */
@Getter
public class MethodInfo {

    // 方法调用者
    private Object handler;
    // 方法自身
    private Method method;
    // 方法的各个形参类型
    private Class<?>[] paramTypes;

    public MethodInfo(Object handler, Method method) {
        this.handler = handler;
        this.method = method;
        this.paramTypes = method.getParameterTypes();
    }

}
