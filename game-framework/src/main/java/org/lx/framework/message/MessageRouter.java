package org.lx.framework.message;

import org.lx.framework.net.session.Session;
import org.lx.framework.protocol.KeyBuilder;
import org.lx.framework.reflect.MethodInfo;
import org.lx.framework.task.CommonThreadPool;
import org.lx.framework.threadpool.AbsLogicExecutor;
import org.lx.framework.threadpool.CmdTask;
import org.lx.framework.util.ChannelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息路由
 * 根据消息类型来获取具体的指令执行器, 并执行之
 */
@Component
public class MessageRouter {

    // 路由表 --- key: module和cmd的组合  value: 方法体
    private final Map<Integer, MethodInfo> messageToMethod = new HashMap<>();

    @Autowired
    private AbsLogicExecutor logicExecutor;

    public void registerMethodInfo(short module, byte cmd, MethodInfo methodInfo) {
        int key = KeyBuilder.buildKey(module, cmd);
        if (messageToMethod.containsKey(key)) {
            throw new RuntimeException(
                    "Handler:" +methodInfo.getHandler().getClass().getName()
                    + "出现重复的module:" + module + "和cmd:" + cmd
            );
        }
        messageToMethod.put(key, methodInfo);
    }

    /**
     * 根据消息对应的指令，执行具体的方法
     * @param message 私有协议消息
     * @param session 连接会话
     */
    public void route(Message message, Session session) {
        final short module = message.getModule();
        final byte cmd = message.getCmd();
        final int key = KeyBuilder.buildKey(module, cmd);
        final MethodInfo methodInfo = messageToMethod.get(key);
        if (methodInfo == null) {
            throw new RuntimeException("找不到对应的MessageInfo, module:" + module + ", cmd:" + cmd);
        }

        // params包含 Session + Message实现类
        final Object[] params = constructParams(methodInfo.getMethod().getParameterTypes(), message, session);
        logicExecutor.exec(new CmdTask(
                methodInfo.getHandler(),
                methodInfo.getMethod(),
                params,
                session.getSessionId())
        );

    }

    /**
     * 创建方法调用(invoke)的参数
     * @param paramTypes 方法参数的类型数组
     * @param message 私有协议消息
     * @param session 连接会话
     * @return 方法参数数组
     */
    private Object[] constructParams(Class<?>[] paramTypes, Message message, Session session) {
        Object[] params = new Object[paramTypes == null ? 0 : paramTypes.length];
        for (int i=0; i<params.length; i++) {
            Class<?> paramType = paramTypes[i];
            if (Message.class.isAssignableFrom(paramType)) {
                params[i] = message;
            } else if (Session.class.isAssignableFrom(paramType)) {
                params[i] = session;
            }
        }
        return params;
    }


}
