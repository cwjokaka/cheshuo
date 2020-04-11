package org.lx.cs.server.game.chat.message.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.chat.ChatCmd;
import org.lx.cs.server.message.MessageResp;
import org.lx.framework.annotation.MessageMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(module = Modules.CHAT, cmd = ChatCmd.WORLD_CHAT_RESP)
public class WorldChatResp extends MessageResp {



}
