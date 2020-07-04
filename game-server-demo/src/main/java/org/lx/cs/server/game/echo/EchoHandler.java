package org.lx.cs.server.game.echo;

import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.echo.message.req.EchoReq;
import org.lx.cs.server.game.echo.message.resp.EchoResp;
import org.lx.framework.annotation.Handler;
import org.lx.framework.annotation.Mapping;

@Handler(module = Modules.ECHO)
public class EchoHandler {

    @Mapping(cmd = EchoCmd.ECHO_REQ)
    public EchoResp echo(EchoReq echoReq) {
        EchoResp echoResp = new EchoResp();
        echoResp.setCode(200);
        echoResp.setText(echoReq.getText());
        return echoResp;
    }

}
