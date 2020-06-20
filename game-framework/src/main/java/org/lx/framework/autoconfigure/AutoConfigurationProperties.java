package org.lx.framework.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "cheshuo")
public class AutoConfigurationProperties {

    private String ip = "localhost";
    @NestedConfigurationProperty
    private ServerConfig server = new ServerConfig();

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ServerConfig getServer() {
        return server;
    }

    public void setServer(ServerConfig server) {
        this.server = server;
    }

}
