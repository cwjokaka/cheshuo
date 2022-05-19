package org.lx.framework.test;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;
import org.lx.framework.codec.ProtobufEncoder;
import org.lx.framework.message.Message;
import org.lx.framework.netty.handler.ProtobufEncodeHandler;

import java.io.IOException;

/**
 * @author tanjl
 * @date 2022/5/18 17:55
 */
public class ProtobufEncodeHandlerTest {

    @Test
    public void protobufEncodeHandlerTest() throws IOException {
        TestMessage testMessage = new TestMessage();
        EmbeddedChannel channel = new EmbeddedChannel(new ProtobufEncodeHandler(new ProtobufEncoder()));
        Assert.assertTrue(channel.writeOutbound(testMessage));
        Assert.assertTrue(channel.finish());

        ByteBuf outBuf = channel.readOutbound();
        System.out.println(outBuf.readableBytes());
        short totalLen = outBuf.readShort();
        Assert.assertEquals(13, totalLen);
        short module = outBuf.readShort();
        Assert.assertEquals(1, module);
        byte cmd = outBuf.readByte();
        Assert.assertEquals(1, cmd);
        int size = outBuf.readableBytes();

        byte[] bytes = new byte[size];
        outBuf.readBytes(bytes);

        Class<?> messageClass = TestMessage.class;
        Codec<?> codec = ProtobufProxy.create(messageClass);
        TestMessage messageResult = (TestMessage) codec.decode(bytes);
        Assert.assertEquals(messageResult.getAge(), 0);

        Assert.assertNull(channel.readOutbound());
    }

}
