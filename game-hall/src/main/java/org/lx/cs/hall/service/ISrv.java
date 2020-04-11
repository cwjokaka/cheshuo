package org.lx.cs.hall.service;

import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.common.response.CommonResp;
import org.lx.cs.hall.protocol.request.MsgProtocol;

public interface ISrv {

    CommonResp<?> execute(ChannelHandlerContext ctx, MsgProtocol data);

}
