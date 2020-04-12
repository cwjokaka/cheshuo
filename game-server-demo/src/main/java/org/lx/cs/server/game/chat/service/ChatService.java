package org.lx.cs.server.game.chat.service;

import org.lx.cs.server.game.chat.message.req.WorldChatReq;
import org.lx.cs.server.game.chat.message.resp.WorldChatResp;
import org.lx.framework.net.session.Session;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    public WorldChatResp worldChat(Session session, WorldChatReq req) {

        return new WorldChatResp();
    }

}
