package org.lx.framework.autoconfigure;

public class WebsocketConfig {

    private boolean enable = false;
    private int port = 8500;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }


}
