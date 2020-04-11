package org.lx.framework.message;

import org.lx.framework.annotation.Handler;
import org.lx.framework.annotation.Mapping;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.reflect.MethodInfo;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 消息处理器注册, 用于初始化以下:
 *  1.MessageRouter: 指令与具体执行方法之间的关联
 */
@Component
public class MessageRegisterProcessor implements BeanPostProcessor {

    private final MessageRouter messageRouter;

    public MessageRegisterProcessor(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (!clazz.isAnnotationPresent(Handler.class)) {
            return bean;
        }
        Handler handlerAnt = clazz.getAnnotation(Handler.class);
        short module = handlerAnt.module();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            byte cmd = getMappingCmd(method);
            if (cmd == -1) {
                continue;
            }
            checkMethodParams(module, cmd, bean, method);
            registerToMessageRouter(module, cmd, bean, method);
        }
        return null;
    }

    private byte getMappingCmd(Method method) {
        if (!method.isAnnotationPresent(Mapping.class)) {
            return -1;
        }

        Mapping annotation = method.getAnnotation(Mapping.class);
        byte cmd = annotation.cmd();
        if (cmd < 0) {
            throw new RuntimeException("cmd不能小于0:" + method.getName());
        }
        return annotation.cmd();
//        for (Class<?> parameterType : method.getParameterTypes()) {
//            if (Message.class.isAssignableFrom(parameterType)) {
//                MessageMeta msgAnt = parameterType.getAnnotation(MessageMeta.class);
//                if (null != msgAnt) {
//                    return msgAnt.cmd();
//                }
//            }
//        }
//        throw new RuntimeException("没有找到该方法对应的cmd号:" + method.getName());
    }

    private void registerToMessageRouter(short module, byte cmd, Object bean, Method method) {
        messageRouter.registerMethodInfo(module, cmd, new MethodInfo(bean, method));
    }

    /**
     * 检测方法中Message的module和cmd是否对的上 Handler与其方法的注解值
     */
    private void checkMethodParams(short module, byte cmd, Object handler, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (int i=0; i<parameterTypes.length; i++) {
            Class<?> paramClass = parameterTypes[i];
            if (Message.class.isAssignableFrom(paramClass)) {
                checkMessageValidity(module, cmd, (Class<? extends Message>) paramClass, handler.getClass().getName(), method.getName());
            }
        }
    }

    /**
     * 校验message合法性
     */
    private void checkMessageValidity(short module, byte cmd, Class<? extends Message> messageClass, String beanName, String methodName) {
        MessageMeta annotation = messageClass.getAnnotation(MessageMeta.class);
        if (module != annotation.module()) {
            throw new RuntimeException(
                    beanName + ":" + methodName + "方法中, 类的module与Message类型参数的module不同");
        }
        if (cmd != annotation.cmd()) {
            throw new RuntimeException(
                    beanName + " 中, 方法"+ methodName + "的cmd与Message类型参数的cmd不同");
        }
    }

}
