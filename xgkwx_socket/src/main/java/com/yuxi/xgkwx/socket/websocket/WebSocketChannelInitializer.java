package com.yuxi.xgkwx.socket.websocket;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Autowired
    private WebSocketHandler webSocketHandler;

    @Value("${websocket.url}")
    private String websocketUrl;

    @Override
    protected void initChannel(SocketChannel socketChannel) {

        //获取pipeline通道
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer("%_%\r\n".getBytes())))
                .addLast(new StringEncoder())
                .addLast(new StringDecoder())
                .addLast(new DelimiterBasedEncoder("%_%\r\n"))
                .addLast(new JsonObjectDecoder());



        // 通过管道，添加handler处理器
        // HttpServerCodec 是由netty自己提供的助手类，此处可以理解为管道中的拦截器
        // 当请求到服务端，我们需要进行做解码，相应到客户端做编码
        // websocket 基于http协议，所以需要有http的编解码器
//        pipeline.addLast(new JsonObjectDecoder());
//        pipeline.addLast(new HttpServerExpectContinueHandler());
        //添加对大数据流的支持，以块方式写，添加ChunkedWriteHandler处理器
//        pipeline.addLast(new ChunkedWriteHandler());

        // 对httpMessage进行聚合，聚合成为FullHttpRequest或FullHttpResponse
        // 几乎在netty的编程中，都会使用到此handler
        // http数据在传输过程中是分段, HttpObjectAggregator ，就是可以将多个段聚合
        // 这就就是为什么，当浏览器发送大量数据时，就会发出多次http请求
//        pipeline.addLast(new HttpObjectAggregator(8192));

        // ==================== 以上是用于支持http协议相关的handler ====================

        // ==================== 增加心跳支持 start ====================
        // 针对客户端，如果在1分钟没有向服务端发送读写心跳(ALL)，则主动断开连接
        // 如果是读空闲或者写空间，不做任何处理
//        pipeline.addLast(new IdleStateHandler(8, 10, 300 * 60));
//        pipeline.addLast(new HeartBeatHandler());
        // ==================== 增加心跳支持 end ====================


        // ==================== 以下是用于支持websocket ====================

        // WebSocket服务器处理的协议，用于指定给客户端连接的时候访问路由： /msg
        // 此Handler会帮我们处理一些比较复杂的繁重的操作
        // 会处理一些握手操作：handShaking（close,ping,pong）ping + pong = 心跳
        // 对于WebSocket来说，数据都是以frames进行传输的，不同的数据类型所对应的frames也都不同
//        pipeline.addLast(new WebSocketServerProtocolHandler(websocketUrl));
        //自定义的handler ，处理业务逻辑
        pipeline.addLast(webSocketHandler);
    }
}
