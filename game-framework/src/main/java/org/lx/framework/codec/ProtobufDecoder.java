package org.lx.framework.codec;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import io.netty.buffer.ByteBuf;
import org.lx.framework.message.Message;
import org.lx.framework.message.MessageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * protobuf解码器
 */
@Component
public class ProtobufDecoder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProtobufDecoder.class);

    private final MessageManager messageManager;

    public ProtobufDecoder(MessageManager messageManager) {
        this.messageManager = messageManager;
    }

    public Message decode(short module, byte cmd, byte[] bytes) {
        Class<?> messageClass = messageManager.getMessageClass(module, cmd);
        Codec<?> codec = ProtobufProxy.create(messageClass);
        Message message = null;
        try {
            message = (Message) codec.decode(bytes);
        } catch (IOException e) {
            LOGGER.error("ProtobufDecoder反序列化失败:", e);
        }
        return message;
    }

    public Message decode(ByteBuf in) {
        short module = in.readShort();
        byte cmd = in.readByte();
        int size = in.readableBytes();
        byte[] bytes = new byte[size];
        in.readBytes(bytes);
        return decode(module, cmd, bytes);
    }

}
