package org.lx.framework.annotation;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解在{@link org.lx.framework.message.Message}的子类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MessageMeta {

    /**
     * 模块号
     */
    short module() default 0;

    /**
     * 指令号
     */
    byte cmd() default 0;

}
