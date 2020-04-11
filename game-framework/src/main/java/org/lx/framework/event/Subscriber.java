package org.lx.framework.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 事件订阅者
 */
public class Subscriber {

    private final Object bean;      // 订阅者对象
    private final Method method;    // 处理事件的方法

    public Subscriber(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public void handle(Event event) {
        try {
            method.invoke(bean, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
