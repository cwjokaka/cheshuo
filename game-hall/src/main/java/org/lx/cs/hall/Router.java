package org.lx.cs.hall;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.common.exception.BizException;
import org.lx.cs.common.response.code.impl.CommonRespCode;
import org.lx.cs.common.util.ResponseUtil;
import org.lx.cs.hall.protocol.request.MsgProtocol;
import org.lx.cs.hall.service.ISrv;
import org.lx.cs.hall.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class Router {

    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);

    public void route(MsgProtocol data, ChannelHandlerContext ctx) {

        if (data.getType() == null) {
            LOGGER.warn("请求来自IP:{}, 没有协议号", ctx.channel().remoteAddress());
            try {
                HttpUtil.writeJson(ctx, ResponseUtil.error());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return;
        }

        ISrv service = data.getType().getSrv();
        try {
            if (service != null) {
                try {
                    HttpUtil.writeJson(ctx, service.execute(ctx, data));
                } catch (BizException e) {
                    HttpUtil.writeJson(ctx, ResponseUtil.error(e.getCode()));
                }
            } else {
                LOGGER.warn("协议类型:{}没有对应的service处理", data.getType().getCode());
                HttpUtil.writeJson(ctx, ResponseUtil.error(CommonRespCode.FAILURE));
            }
        } catch (JsonProcessingException e) {
            LOGGER.error("JSON转换失败:{}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("发生错误:{}", e.getMessage());
        }
    }

}
