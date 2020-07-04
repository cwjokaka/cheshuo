package org.lx.cs.server.game.echo.message.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.chat.ChatCmd;
import org.lx.cs.server.game.echo.EchoCmd;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.message.Message;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(module = Modules.ECHO, cmd = EchoCmd.ECHO_REQ)
public class EchoReq extends Message {

    private String text;

}
