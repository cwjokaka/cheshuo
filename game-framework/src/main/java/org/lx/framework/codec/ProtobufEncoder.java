package org.lx.framework.codec;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import org.lx.framework.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * protobuf编码器
 */
@Component
public class ProtobufEncoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtobufEncoder.class);

    public byte[] encode(Message message) {
        //写入具体消息的内容
        byte[] body = null;
        Class<Message> msgClazz = (Class<Message>) message.getClass();
        try {
            Codec<Message> codec = ProtobufProxy.create(msgClazz);
            body = codec.encode(message);
        } catch (IOException e) {
            LOGGER.error("", e);
        }
        return body;
    }

}
