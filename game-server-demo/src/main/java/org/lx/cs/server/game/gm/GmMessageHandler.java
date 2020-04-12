package org.lx.cs.server.game.gm;

import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.gm.message.GmMessage;
import org.lx.cs.server.game.gm.message.req.GmKickReq;
import org.lx.cs.server.game.gm.service.GmService;
import org.lx.cs.server.message.MessageResp;
import org.lx.framework.annotation.Handler;
import org.lx.framework.annotation.Mapping;
import org.lx.framework.net.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Handler(module = Modules.GM)
public class GmMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmMessageHandler.class);

    private final GmService gmService;

    public GmMessageHandler(GmService gmService) {
        this.gmService = gmService;
    }

    @Mapping(cmd = GmCmd.GM_REQ)
    public GmMessage execGmMessage(Session session, GmMessage message) {
        LOGGER.info("收到GmMessage,id:{}", message.getId());
        return new GmMessage();
    }

    @Mapping(cmd = GmCmd.GM_KICK_REQ)
    public MessageResp gmKick(GmKickReq req) {
        return gmService.gmKick(req);
    }



}
