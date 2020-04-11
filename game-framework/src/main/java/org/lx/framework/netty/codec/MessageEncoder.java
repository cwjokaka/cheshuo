//package org.lx.framework.netty.codec;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.MessageToByteEncoder;
//import org.lx.framework.codec.ProtobufEncoder;
//import org.lx.framework.message.Message;
//
//
//public class MessageEncoder extends MessageToByteEncoder<Message> {
//
//    @Override
//    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
//        byte[] paramBody = new ProtobufEncoder().encode(msg);
//        short module = msg.getModule();
//        byte cmd = msg.getCmd();
//        // 协议总长度 = total_len(short) + module(byte) + cmd(byte) + param_body
//        out.writeInt(paramBody.length + 4);
//        out.writeByte(module);
//        out.writeByte(cmd);
//        out.writeBytes(paramBody);
//    }
//
//}
