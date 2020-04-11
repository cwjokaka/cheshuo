package org.lx.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 处理器{@link Handler}内的方法注解，标有这个注解的方法会根据其参数{@link org.lx.framework.message.Message}
 * 实现类的不同而被调用
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

    byte cmd();

}
