package org.lx.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    protected static ApplicationContext ctx = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    public static Object getBean(String beanId) {
        return ctx.getBean(beanId);
    }

    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }



}