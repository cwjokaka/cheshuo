package org.lx.framework.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "cheshuo")
public class AutoConfigurationProperties {

    private String host = "0.0.0.0";
    @NestedConfigurationProperty
    private ServerConfig server = new ServerConfig();

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public ServerConfig getServer() {
        return server;
    }

    public void setServer(ServerConfig server) {
        this.server = server;
    }

}
