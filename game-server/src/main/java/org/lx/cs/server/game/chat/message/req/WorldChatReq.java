package org.lx.cs.server.game.chat.message.req;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.chat.ChatCmd;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.message.Message;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(module = Modules.CHAT, cmd = ChatCmd.WORLD_CHAT_REQ)
public class WorldChatReq extends Message {

    private String text;

}
