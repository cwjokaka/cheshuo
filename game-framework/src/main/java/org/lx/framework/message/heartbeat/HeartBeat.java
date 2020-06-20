package org.lx.framework.message.heartbeat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.message.Message;

@MessageMeta(module = 0, cmd = 65)
public class HeartBeat extends Message {

    private int code = 1;

}
