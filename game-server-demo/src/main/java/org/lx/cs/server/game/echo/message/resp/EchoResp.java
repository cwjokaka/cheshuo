package org.lx.cs.server.game.echo.message.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.chat.ChatCmd;
import org.lx.cs.server.game.echo.EchoCmd;
import org.lx.cs.server.message.MessageResp;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.message.Message;

@Accessors(chain = true)
//@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(module = Modules.ECHO, cmd = EchoCmd.ECHO_RESP)
public class EchoResp extends Message {
    private int code;
    private String text;
}
