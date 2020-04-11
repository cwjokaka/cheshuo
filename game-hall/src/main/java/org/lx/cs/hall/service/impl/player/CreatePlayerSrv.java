package org.lx.cs.hall.service.impl.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.common.response.CommonResp;
import org.lx.cs.common.util.JsonUtil;
import org.lx.cs.common.util.ResponseUtil;
import org.lx.cs.hall.entity.Player;
import org.lx.cs.hall.protocol.request.MsgProtocol;
import org.lx.cs.hall.protocol.request.data.CreateRoleReq;
import org.lx.cs.hall.protocol.response.code.impl.PlayerRespCode;
import org.lx.cs.hall.service.ISrv;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CreatePlayerSrv implements ISrv {

    private final MongoTemplate mongoTemplate;

    public CreatePlayerSrv(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public CommonResp<?> execute(ChannelHandlerContext ctx, MsgProtocol data) {
        Long userId = data.getUserId();
        Player find = mongoTemplate.findById(userId, Player.class);
        if (find != null) {
            return ResponseUtil.error(PlayerRespCode.PLAYER_NOT_FOUND);
        }
        JsonNode node = data.getData();
        CreateRoleReq params = null;
        try {
            params = JsonUtil.nodeToObj(node, CreateRoleReq.class);
            String name = params.getName();
            Player player = new Player();
            player.setName(name);
            player.set_id(userId);
            player.setHeroList(new ArrayList<>());
            player.setCoin(100);
            player.setCups(0);
            mongoTemplate.insert(player);
            return ResponseUtil.ok();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
