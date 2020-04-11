package org.lx.cs.hall.service.impl.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.common.exception.BizException;
import org.lx.cs.common.response.CommonResp;
import org.lx.cs.common.util.ResponseUtil;
import org.lx.cs.hall.entity.Player;
import org.lx.cs.hall.protocol.request.MsgProtocol;
import org.lx.cs.hall.protocol.response.code.impl.PlayerRespCode;
import org.lx.cs.hall.service.ISrv;
import org.lx.cs.hall.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class QueryPlayerInfoSrv implements ISrv {

    private final static Logger LOGGER = LoggerFactory.getLogger(QueryPlayerInfoSrv.class);

    private final MongoTemplate mongoTemplate;

    public QueryPlayerInfoSrv(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public CommonResp<?> execute(ChannelHandlerContext ctx, MsgProtocol data) {
        Player player = mongoTemplate.findById(data.getUserId(), Player.class);
        if (player == null) {
            LOGGER.warn("找不到用户信息, ID:{}", data.getUserId());
            throw new BizException(PlayerRespCode.PLAYER_NOT_FOUND);
        }
        try {
            HttpUtil.writeJson(ctx, ResponseUtil.ok(player));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
