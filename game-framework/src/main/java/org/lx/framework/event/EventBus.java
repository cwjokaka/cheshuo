package org.lx.framework.event;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 事件总线
 */
@Component
public class EventBus {

    private final Executor executor = Executors.newSingleThreadExecutor(new DefaultThreadFactory("EventBus"));

    public void register(Object bean) {
        SubscriberRegistry.INSTANCE.register(bean);
    }

    /**
     * 同步处理事件
     */
    public void syncSubmit(Event event) {
        List<Subscriber> subscribers = SubscriberRegistry.INSTANCE.getSubscribersByEvent(event.getClass());
        subscribers.forEach( subscriber -> subscriber.handle(event));
    }

    /**
     * 异步处理事件
     */
    public void asyncSubmit(Event event) {
        List<Subscriber> subscribers = SubscriberRegistry.INSTANCE.getSubscribersByEvent(event.getClass());
        subscribers.forEach(subscriber ->
                executor.execute(() ->
                        subscriber.handle(event)
                )
        );
    }

}
