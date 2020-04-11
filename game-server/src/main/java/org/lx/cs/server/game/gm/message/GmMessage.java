package org.lx.cs.server.game.gm.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.gm.GmCmd;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.message.Message;

@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(module = Modules.GM, cmd = GmCmd.GM_REQ)
public class GmMessage extends Message {

    private int id = 0;

}
