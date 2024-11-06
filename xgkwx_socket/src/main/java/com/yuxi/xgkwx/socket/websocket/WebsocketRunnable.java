package com.yuxi.xgkwx.socket.websocket;

import com.yuxi.xgkwx.socket.msg.MessageRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class WebsocketRunnable implements Runnable{
    private final static Logger log = LoggerFactory.getLogger(WebsocketRunnable.class);
    private ChannelHandlerContext channelHandlerContext;

    private MessageRequest messageRequest;

    public WebsocketRunnable(ChannelHandlerContext channelHandlerContext, MessageRequest messageRequest) {
        this.channelHandlerContext = channelHandlerContext;
        this.messageRequest = messageRequest;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName()+"--"+ LocalDateTime.now());
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(LocalDateTime.now().toString()));
        } catch (Exception e) {
            log.error("websocket服务器推送消息发生错误：",e);
        }
    }
}
