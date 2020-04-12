package org.lx.cs.server.game.chat;

public interface ChatCmd {

    byte PRIVATE_CHAT_REQ = 0;
    byte PRIVATE_CHAT_RESP = 64;

    byte WORLD_CHAT_REQ = 1;
    byte WORLD_CHAT_RESP = 65;

}
