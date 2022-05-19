package org.lx.framework.test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.message.Message;

/**
 * @author tanjl
 * @date 2022/5/19 09:55
 */
@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(cmd = 1, module = 1)
public class TestMessage extends Message {

    private int age = 0;

    private String orderSn = "005465";

}
