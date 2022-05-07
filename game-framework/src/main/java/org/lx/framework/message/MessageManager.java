package org.lx.framework.message;

import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.protocol.KeyBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * @author cwjokaka
 * 自定义消息管理器,用于保存 指令ID(由module + cmd组成)与Message具体类型之间的联系
 */
@Component
public class MessageManager {

    /**
     * 指令ID和消息类信息的映射关系
     */
    private final Map<Integer, Class<? extends Message>> idToClass = new HashMap<>();

    /**
     * 消息类信息和指令ID的映射关系
     */
    private final Map<Class<?>, Integer> classToId = new HashMap<>();

    /**
     * 注册消息类
     * @param clazz 消息类
     * @return 是否注册成功
     */
    public boolean register(Class<?> clazz) {
        MessageMeta meta = clazz.getAnnotation(MessageMeta.class);
        if (meta == null) {
            return false;
        }
        int id = KeyBuilder.buildKey(meta.module() , meta.cmd());
        if (idToClass.containsKey(id)) {
            throw new RuntimeException("待注册的Message:" + clazz.getName()
                    + "与已存在的Message:" + idToClass.get(id).getName()
                    + " 发生冲突： 重复的module:" + meta.module() + ", cmd:" + meta.cmd()
            );
        }
        //noinspection unchecked
        idToClass.put(id, (Class<? extends Message>) clazz);
        classToId.put(clazz, id);
        return true;
    }

    public Class<? extends Message> getMessageClass(short module, byte cmd) {
        return idToClass.get(KeyBuilder.buildKey(module, cmd));
    }

}
