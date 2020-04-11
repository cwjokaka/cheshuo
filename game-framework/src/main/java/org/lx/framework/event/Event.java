package org.lx.framework.event;

/**
 * 事件基类
 */
public interface Event {

    default Object getData() {
        return null;
    }

}
