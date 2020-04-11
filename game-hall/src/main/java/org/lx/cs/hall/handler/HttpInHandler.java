package org.lx.cs.hall.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.util.CharsetUtil;
import org.lx.cs.common.util.JsonUtil;
import org.lx.cs.hall.Router;
import org.lx.cs.hall.protocol.request.MsgProtocol;
import org.lx.cs.hall.task.CommonThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class HttpInHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpInHandler.class);

    private final CommonThreadPool commonThreadPool;
    private final Router router;

    public HttpInHandler(CommonThreadPool commonThreadPool, Router router) {
        this.commonThreadPool = commonThreadPool;
        this.router = router;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            commonThreadPool.execute(() -> {
                FullHttpRequest httpRequest = (FullHttpRequest) msg;
                if (httpRequest.method() == HttpMethod.GET){
                    handleGet(ctx, httpRequest);
                } else if (httpRequest.method() == HttpMethod.POST) {
                    handlePost(ctx, httpRequest);
                }

            });
        }
    }

    private void handleGet(ChannelHandlerContext ctx, FullHttpRequest request) {
        QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
        Map<String, List<String>> params = decoder.parameters();
        List<String> typeList = params.get("type");
        LOGGER.info("ip:{},read :{}", ctx.channel().remoteAddress(), typeList.get(0));
        ctx.writeAndFlush("not support method");
    }

    private void handlePost(ChannelHandlerContext ctx, FullHttpRequest request) {
        HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
        // 逻辑接口处理
        try {
//            InterfaceHttpData data = decoder.getBodyHttpData("data");
            ByteBuf jsonBuf = request.content();
            String dataStr = jsonBuf.toString(CharsetUtil.UTF_8);
            if (dataStr != null) {
                LOGGER.info("ip:{},read:{}", ctx.channel().remoteAddress(), dataStr);
                router.route(JsonUtil.strToObj(dataStr, MsgProtocol.class), ctx);
            }
        }
        catch (JsonProcessingException e) {
            LOGGER.error("解析JSON出错:{}", e.getMessage());
        } catch (Exception e) {
            LOGGER.error("post error msg:", e);
            e.printStackTrace();
        }


    }

}
