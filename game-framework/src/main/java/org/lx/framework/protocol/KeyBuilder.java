package org.lx.framework.protocol;

/**
 * 定义协议Key的组成规则
 */
public class KeyBuilder {

    public static int buildKey(short module, byte cmd) {
        return module * 1000 + cmd;
    }

}
