package org.lx.cs.server.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lx.framework.message.Message;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class MessageResp extends Message {

    private int code;

}
