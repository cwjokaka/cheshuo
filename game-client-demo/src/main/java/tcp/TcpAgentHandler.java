package tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class TcpAgentHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {

    private final Map<Channel, TcpClient> distributes = new ConcurrentHashMap<>();
    private final static TcpAgentHandler INSTANCE = new TcpAgentHandler();

    private TcpAgentHandler(){}

    public static TcpAgentHandler getInstance() {
        return INSTANCE;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
        System.out.println("TcpAgentHandler:收到消息");
        TcpClient tcpClient = distributes.get(ctx.channel());
        int length = msg.content().readableBytes();
        byte[] data = new byte[length];
        msg.content().readBytes(data);
        System.out.println("TcpAgentHandler:转发消息给Tcp服务器..." + new String(data));
        tcpClient.sendMessage(data);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("TcpAgent:建立了新连接, 创建TcpClient连接服务器...");
        TcpClient client = new TcpClient();
        distributes.put(ctx.channel(), client);
        new Thread(() -> {
            try {
                client.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        distributes.remove(ctx.channel());
    }
}
