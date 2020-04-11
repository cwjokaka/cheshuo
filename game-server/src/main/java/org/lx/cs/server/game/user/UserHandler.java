package org.lx.cs.server.game.user;

import org.lx.cs.server.game.Modules;
import org.lx.cs.server.game.user.message.req.LoginReq;
import org.lx.cs.server.game.user.message.req.RegisterReq;
import org.lx.cs.server.game.user.message.resp.LoginResp;
import org.lx.cs.server.game.user.message.resp.LogoutResp;
import org.lx.cs.server.game.user.message.resp.RegisterResp;
import org.lx.cs.server.game.user.service.UserService;
import org.lx.framework.annotation.Handler;
import org.lx.framework.annotation.Mapping;
import org.lx.framework.net.session.Session;

@Handler(module = Modules.USER)
public class UserHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    // 注册
    @Mapping(cmd = UserCmd.REGISTER_REQ)
    public RegisterResp register(Session session, RegisterReq registerReq) {
        String account = registerReq.getAccount();
        String password = registerReq.getPassword();
        return userService.registerUser(account, password);
    }

    // 登录
    @Mapping(cmd = UserCmd.LOGIN_REQ)
    public LoginResp login(Session session, LoginReq loginReq) {
        return userService.login(session, loginReq);
    }

    // 登出
    @Mapping(cmd = UserCmd.LOGOUT_REQ)
    public LogoutResp logout(Session session) {
        return userService.logout(session);
    }

}
