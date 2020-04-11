package org.lx.framework.message;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 消息类型注册, 用于初始化指令ID(module + cmd)与具体消息类型之间的关系，现主要在protobuf编解码中需要用到
 */
@Component
public class MessageClassRegisterProcessor implements BeanPostProcessor {

    private final MessageManager messageManager;

    public MessageClassRegisterProcessor(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (messageManager.register(bean.getClass())) {
            return null;
        }
        return bean;
    }

}
