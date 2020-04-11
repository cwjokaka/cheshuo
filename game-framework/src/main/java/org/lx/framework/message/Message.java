package org.lx.framework.message;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import org.lx.framework.annotation.MessageMeta;

/**
 * 自定义消息基类
 * ProtobufClass这个注解不能在其他注解上用，暂时只能通过继承来实现
 */
@ProtobufClass
public abstract class Message {

    public short getModule() {
        MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
        if (annotation != null) {
            return annotation.module();
        }
        return 0;
    }

    public byte getCmd() {
        MessageMeta annotation = getClass().getAnnotation(MessageMeta.class);
        if (annotation != null) {
            return annotation.cmd();
        }
        return 0;
    }

}
