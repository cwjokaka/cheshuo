package org.lx.framework.autoconfigure;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

public class ServerConfig {

    @NestedConfigurationProperty
    private TcpConfig tcp = new TcpConfig();
    @NestedConfigurationProperty
    private WebsocketConfig websocket = new WebsocketConfig();

    public TcpConfig getTcp() {
        return tcp;
    }

    public void setTcp(TcpConfig tcp) {
        this.tcp = tcp;
    }

    public WebsocketConfig getWebsocket() {
        return websocket;
    }

    public void setWebsocket(WebsocketConfig websocket) {
        this.websocket = websocket;
    }
}
