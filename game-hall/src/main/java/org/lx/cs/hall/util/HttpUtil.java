package org.lx.cs.hall.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.lx.cs.common.response.CommonResp;
import org.lx.cs.common.util.JsonUtil;

public class HttpUtil {

    public static <T extends CommonResp<?>> void writeJson(ChannelHandlerContext ctx, T msg) throws JsonProcessingException {
        writeJson(ctx, msg, HttpResponseStatus.OK);
    }

    public static <T extends CommonResp<?>> void writeJson(ChannelHandlerContext ctx, T msg, HttpResponseStatus status) throws JsonProcessingException {
//        ByteBuf content = Unpooled.copiedBuffer(JsonUtil.toJsonStr(msg), CharsetUtil.UTF_8);
        // wrappedBuffer实现零拷贝
        ByteBuf content = Unpooled.wrappedBuffer(JsonUtil.toJsonStr(msg).getBytes());
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, content);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json; charset=utf-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
        ctx.writeAndFlush(response);
    }

}
