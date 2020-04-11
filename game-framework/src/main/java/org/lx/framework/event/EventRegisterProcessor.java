package org.lx.framework.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 事件注册
 *  Spring bean初始化时调用
 */
@Component
public class EventRegisterProcessor implements BeanPostProcessor {

    private final EventBus eventBus;

    public EventRegisterProcessor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        eventBus.register(bean);
        return bean;
    }

}
