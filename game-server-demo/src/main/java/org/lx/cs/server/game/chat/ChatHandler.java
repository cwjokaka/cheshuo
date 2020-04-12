package org.lx.cs.server.game.chat;

import org.lx.cs.server.game.chat.message.req.PrivateChatReq;
import org.lx.cs.server.game.chat.message.req.WorldChatReq;
import org.lx.cs.server.game.chat.message.resp.PrivateChatResp;
import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.chat.message.resp.WorldChatResp;
import org.lx.framework.annotation.Handler;
import org.lx.framework.annotation.Mapping;
import org.lx.framework.net.session.Session;
import org.lx.framework.net.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Handler(module = Modules.CHAT)
public class ChatHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatHandler.class);

    private final SessionManager sessionManager;


    public ChatHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Mapping(cmd = ChatCmd.PRIVATE_CHAT_REQ)
    public PrivateChatResp privateChat(Session session, PrivateChatReq req) {
        LOGGER.info("收到ChatMessage:{}", req.getText());
        return new PrivateChatResp().setFromUserId(2L);
    }

    @Mapping(cmd = ChatCmd.WORLD_CHAT_REQ)
    public WorldChatResp worldChat(Session session, WorldChatReq req) {
        LOGGER.info("收到ChatMessage:{}", req.getText());

        return null;
    }

}
