package org.lx.cs.server.game.user.message.resp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.user.UserCmd;
import org.lx.cs.server.message.MessageResp;
import org.lx.framework.annotation.MessageMeta;
import org.lx.framework.message.Message;

@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Data
@MessageMeta(module = Modules.USER, cmd = UserCmd.LOGIN_RESP)
public class LoginResp extends MessageResp {



}
