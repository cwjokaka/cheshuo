package org.lx.framework.net.channel;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class ChannelLifeCycleRegisterProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (ChannelLifeCycle.class.isAssignableFrom(clazz)) {
            ChannelLifeCycleLink.INSTANCE.addChannelLiftCycle((ChannelLifeCycle) bean);
        }
        return bean;
    }
}
