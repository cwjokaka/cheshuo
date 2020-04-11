package org.lx.framework.event;

import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 订阅者注册处
 * 用于保存事件与订阅者的关联信息
 */
public enum SubscriberRegistry {

    INSTANCE;

    private final Map<Class<? extends Event>, List<Subscriber>> eventToSubscribers = new ConcurrentHashMap<>();

    /**
     * 注册bean上被{@link Subscribe}注解的方法
     * @param bean 被Spring注解({@link Component | Service | Controller})标识的Bean
     */
    @SuppressWarnings("unchecked")
    protected void register(Object bean) {
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("订阅事件参数必须且只能有1个," + clazz.getSimpleName() + ":" + method.getName());
                }
                eventToSubscribers.putIfAbsent((Class<? extends Event>)parameterTypes[0], new CopyOnWriteArrayList<>());
                List<Subscriber> subscribers = eventToSubscribers.get(parameterTypes[0]);
                subscribers.add(new Subscriber(bean, method));
            }
        }
    }

    /**
     * 通过事件类型获取所有与此事件有关的订阅者
     * @param eventType 事件类型
     * @return 与此事件有关的订阅者
     */
    protected List<Subscriber> getSubscribersByEvent(Class<? extends Event> eventType) {
        return eventToSubscribers.getOrDefault(eventType, Collections.emptyList());
    }


}
