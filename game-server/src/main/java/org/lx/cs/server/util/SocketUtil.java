package org.lx.cs.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.lx.cs.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SocketUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketUtil.class);

    public static void writeJson(ChannelHandlerContext ctx, Object msg) throws JsonProcessingException {
        String sentMsg = JsonUtil.toJsonStr(msg);
        LOGGER.debug("返回响应数据:{}", sentMsg);
        ctx.writeAndFlush(sentMsg);
    }

    public static void writeJson(Channel channel, Object msg) throws JsonProcessingException {
        String sentMsg = JsonUtil.toJsonStr(msg);
        LOGGER.debug("返回响应数据:{}", sentMsg);
        channel.writeAndFlush(sentMsg);
    }

    public static void write(Channel channel, Object msg) {
        LOGGER.debug("返回响应数据:{}", msg);
        channel.writeAndFlush(msg);
    }

    public static void write(ChannelHandlerContext ctx, Object msg) {
        LOGGER.debug("返回响应数据:{}", msg);
        ctx.writeAndFlush(msg);
    }

}
