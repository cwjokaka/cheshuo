package org.lx.cs.server.game.gm.service;

import org.lx.cs.server.game.gm.message.req.GmKickReq;
import org.lx.cs.server.game.gm.message.resp.GmKickResp;
import org.lx.cs.server.game.user.UserManager;
import org.lx.framework.net.session.Session;
import org.lx.framework.net.session.SessionManager;
import org.lx.framework.util.ChannelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmService.class);

    private final UserManager userManager;

    private final SessionManager sessionManager;

    public GmService(UserManager userManager, SessionManager sessionManager) {
        this.userManager = userManager;
        this.sessionManager = sessionManager;
    }

    /**
     * 强制用户下线
     */
    public GmKickResp gmKick(GmKickReq req) {
        LOGGER.info("收到GmKickReq,userId:{}", req.getUserId());
        String userId = req.getUserId();
        Long sid = userManager.getOnlineSessionId(userId);
        Session userSession = sessionManager.getSession(sid);
        GmKickResp resp = new GmKickResp();
        if (userSession != null) {
            ChannelUtil.closeChannel(userSession.getChannel());
            resp.setCode(200);
            return resp;
        }
        resp.setCode(400);
        return resp;
    }

}
