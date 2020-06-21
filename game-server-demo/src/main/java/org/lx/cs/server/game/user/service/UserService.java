package org.lx.cs.server.game.user.service;

import org.lx.cs.server.game.user.UserManager;
import org.lx.cs.server.game.user.entity.User;
import org.lx.cs.server.game.user.message.req.LoginReq;
import org.lx.cs.server.game.user.message.resp.LoginResp;
import org.lx.cs.server.game.user.message.resp.LogoutResp;
import org.lx.cs.server.game.user.message.resp.RegisterResp;
import org.lx.framework.net.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final MongoTemplate mongoTemplate;

    private final UserManager userManager;

    public UserService(MongoTemplate mongoTemplate, UserManager userManager) {
        this.mongoTemplate = mongoTemplate;
        this.userManager = userManager;
    }

    /**
     * 用户注册
     * @param account 账户名
     * @param password 密码
     */
    public RegisterResp registerUser(String account, String password) {
        LOGGER.info("用户注册account:{}, password:{}", account, password);
        User query = mongoTemplate.findOne(new Query(Criteria.where("account").is(account)), User.class);
        if (null != query) {
            return new RegisterResp().setCode(400);
        }
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        mongoTemplate.insert(new User().setAccount(account).setPassword(password));
        return new RegisterResp().setCode(200);
    }

    /**
     * 用户登录
     */
    public LoginResp login(Session session, LoginReq loginReq) {
        LOGGER.info("用户登录account:{}, password:{}", loginReq.getAccount(), loginReq.getPassword());
        User query = mongoTemplate.findOne(new Query(
                Criteria.where("account").is(loginReq.getAccount())
                .and("password").is(loginReq.getPassword())),
                User.class);
        LoginResp loginResp = new LoginResp();
        if (null == query) {
            loginResp.setCode(400);
            return loginResp;
        }
        LOGGER.info("用户id:{}, account:{} 成功登录", query.getId(), query.getAccount());
        userManager.addOnlineUser(session, query);
        loginResp.setCode(200);
        return loginResp;
    }

    /**
     * 用户登出
     */
    public LogoutResp logout(Session session) {
//        String userId = (String) session.getUserId();
        LogoutResp resp = new LogoutResp();
//        if (userId == null) {
//            resp.setCode(400);
//            return resp;
//        }
//        session.setUserId(null);
//        sessionManager.removeUser(userId, session);
        resp.setCode(200);
        return resp;
    }

}
