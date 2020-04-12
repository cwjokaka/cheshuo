package org.lx.cs.server.game.user;

public interface UserCmd {

    byte LOGIN_REQ = 0;
    byte LOGIN_RESP = 64;

    byte LOGOUT_REQ = 1;
    byte LOGOUT_RESP = 65;

    byte REGISTER_REQ = 2;
    byte REGISTER_RESP = 66;

}
