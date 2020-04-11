package org.lx.framework.net.session;

import io.netty.channel.Channel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.lx.framework.enums.ChannelType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接会话
 */
@Accessors(chain = true)
@Data
public class Session {

    // 会话ID
    private final Long sessionId;

    private Channel channel;

    // Session额外附加属性
    private Map<String, Object> attributes = new ConcurrentHashMap<>();

    // 远程连接的IP
    private String ip;

    private ChannelType channelType;

    public Session(Long sessionId) {
        this.sessionId = sessionId;
    }

    public void setAttr(String key, Object value) {
        attributes.put(key, value);
    }

    public Object removeAttr(String key) {
        return attributes.remove(key);
    }

}
