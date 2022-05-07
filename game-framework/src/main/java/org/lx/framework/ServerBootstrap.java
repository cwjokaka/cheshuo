package org.lx.framework;

import org.lx.framework.netty.server.IServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 服务器引导，用于启动所有协议服务器
 * @author cwjokaka
 */
public class ServerBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBootstrap.class);

    private final Map<String, IServer> serverList;

    public ServerBootstrap(Map<String, IServer> serverList) {
        this.serverList = serverList;
    }

    public void startAll() {
        LOGGER.info("开启游戏服务器...");
        serverList.forEach((name, server) -> {
            LOGGER.info("启动协议服务器:{}", name);
            server.start();
        });
    }

}
