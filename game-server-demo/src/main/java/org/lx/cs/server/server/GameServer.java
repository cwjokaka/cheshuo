package org.lx.cs.server.server;

import org.lx.framework.enums.ChannelType;
import org.lx.framework.netty.server.IServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;

@Component("gameServer")
public class GameServer implements IServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameServer.class);

    private final IServer websocketServer;
//    private final IServer tcpSocketServer;

    private Map<ChannelType, IServer> serverMap = new EnumMap<>(ChannelType.class);

    public GameServer(
            @Qualifier("webSocketServer") IServer websocketServer
//                      @Qualifier("tcpSocketServer") IServer tcpSocketServer
    ) {
        this.websocketServer = websocketServer;
//        this.tcpSocketServer = tcpSocketServer;
//        serverMap.put(ChannelType.TCP, this.tcpSocketServer);
        serverMap.put(ChannelType.WEBSOCKET, this.websocketServer);
    }

    @Override
    public void start() {
        LOGGER.info("开启游戏服务器...");
        for (Map.Entry<ChannelType, IServer> entry : serverMap.entrySet()) {
            LOGGER.info("开启协议服务器:{}", entry.getValue().getClass().getSimpleName());
            entry.getValue().start();
        }
    }


}
