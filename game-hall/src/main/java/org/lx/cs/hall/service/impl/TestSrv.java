package org.lx.cs.hall.service.impl;

import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.common.response.CommonResp;
import org.lx.cs.common.util.ResponseUtil;
import org.lx.cs.hall.protocol.request.MsgProtocol;
import org.lx.cs.hall.service.ISrv;
import org.springframework.stereotype.Component;

@Component
public class TestSrv implements ISrv {

    @Override
    public CommonResp<?> execute(ChannelHandlerContext ctx, MsgProtocol data) {
        return ResponseUtil.ok();
    }

}
