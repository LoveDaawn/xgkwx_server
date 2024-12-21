package com.yuxi.xgkwx.socket.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class DelimiterBasedEncoder extends MessageToMessageEncoder<CharSequence> {
    private final ByteBuf delimiter;

    // 构造函数接收一个分隔符字节数组
    public DelimiterBasedEncoder(String delimiter) {
        this.delimiter = Unpooled.copiedBuffer(delimiter.getBytes()); // 将分隔符字符串转换为字节数组
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
        // 将消息（CharSequence）转换为 ByteBuf
        ByteBuf messageBytes = Unpooled.copiedBuffer(msg, StandardCharsets.UTF_8);
        // 将消息添加到输出列表
        out.add(messageBytes);
        //写入并传送数据
        out.add(delimiter.retain());
    }
}
