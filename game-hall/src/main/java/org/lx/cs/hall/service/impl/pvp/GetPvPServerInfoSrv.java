package org.lx.cs.hall.service.impl.pvp;

import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.common.response.CommonResp;
import org.lx.cs.common.util.ResponseUtil;
import org.lx.cs.hall.protocol.request.MsgProtocol;
import org.lx.cs.hall.service.ISrv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GetPvPServerInfoSrv implements ISrv {

    @Value("${slg.pvp.ip}")
    private String pvpIp;
    @Value("${slg.pvp.port}")
    private String port;

    @Override
    public CommonResp<?> execute(ChannelHandlerContext ctx, MsgProtocol data) {
        List<Object> ret = new ArrayList<>();
        ret.add(pvpIp);
        ret.add(port);
        return ResponseUtil.ok(ret);
    }
}
