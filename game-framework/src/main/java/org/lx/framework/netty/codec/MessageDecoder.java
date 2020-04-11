//package org.lx.framework.netty.codec;
//
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//import org.lx.framework.codec.ProtobufDecoder;
//
//import java.util.List;
//
///**
// * 消息解码器
// * 实现:
// *      粘包/拆包
// *      私有协议栈
// */
//public class MessageDecoder extends ByteToMessageDecoder {
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        System.out.println("接收到数据:" + in.toString());
//        byte[] bytes = new byte[in.readableBytes()];
//        int readerIndex = in.readerIndex();
//        in.getBytes(readerIndex, bytes);
////        System.out.println(new String(bytes));
//        /**
//         * 自定义的协议组成:
//         *  total_len(short)     协议总长度
//         *  module(short)        模块号
//         *  cmd(byte)            指令号
//         *  param_body(byte[])   Protobuf序列化后的byte[]
//         */
//
//        // 完整的协议至少含有total_len + module + cmd
//        int read = in.readableBytes();
//        if (in.readableBytes() < 5) {
//            return;
//        }
//        in.markReaderIndex();
//        short totalLen = in.readShort();
//        if (totalLen - 2 > in.readableBytes()) {
//            in.resetReaderIndex();
//            return;
//        }
//        byte module = in.readByte();
//        byte cmd = in.readByte();
//        int remain = totalLen - 5;
//        byte[] paramBody = new byte[remain];
//        in.readBytes(paramBody);
//        if (paramBody.length > 0) {
//            ProtobufDecoder decoder = new ProtobufDecoder();
//            // 反序列化为Message
//            Object body = decoder.decode(module, cmd, paramBody);
//            out.add(body);
//        }
//    }
//
//}
