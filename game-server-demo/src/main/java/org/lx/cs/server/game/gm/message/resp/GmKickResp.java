package org.lx.cs.server.game.gm.message.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.gm.GmCmd;
import org.lx.cs.server.message.MessageResp;
import org.lx.framework.annotation.MessageMeta;

@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(module = Modules.GM, cmd = GmCmd.GM_KICK_RESP)
public class GmKickResp extends MessageResp {

}
