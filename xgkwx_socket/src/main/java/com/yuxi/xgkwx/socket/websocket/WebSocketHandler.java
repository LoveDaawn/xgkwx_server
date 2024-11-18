package com.yuxi.xgkwx.socket.websocket;

import com.alibaba.fastjson.JSON;
import com.yuxi.xgkwx.socket.msg.MessageRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 自定义助手类
 * SimpleChannelInboundHandler: 对于请求来说，相当于入站(入境)
 * TextWebSocketFrame: 用于为websocket专门处理的文本数据对象，Frame是数据(消息)的载体
 */
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private final static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    //通道map，存储channel，用于群发消息，以及统计客户端的在线数量，解决问题问题三，如果是集群环境使用redis的hash数据类型存储即可
    private static Map<String, Channel> channelMap = new ConcurrentHashMap<>();
    private static Map<String, Future> futureMap = new ConcurrentHashMap<>();
    //存储channel的id和用户主键的映射，客户端保证用户主键传入的是唯一值，解决问题四，如果是集群中需要换成redis的hash数据类型存储即可
    private static Map<String, Long> clientMap = new ConcurrentHashMap<>();
    // 用于记录和管理所有客户端的channel组
    public static ChannelGroup clients =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 客户端发送给服务端的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {

        try {
            //接受客户端发送的消息
            MessageRequest messageRequest = JSON.parseObject(msg.text(), MessageRequest.class);
            log.info("接受到的数据：{}", messageRequest);
            //每个channel都有id，asLongText是全局channel唯一id
            String key = ctx.channel().id().asLongText();
            clientMap.put(key, messageRequest.getUnionId());
            log.info("接受客户端的消息......" + ctx.channel().remoteAddress() + "-参数[" + messageRequest.getUnionId() + "]");

            if (!channelMap.containsKey(key)) {
                //使用channel中的任务队列，做周期循环推送客户端消息，解决问题二和问题五
                Future future = ctx.channel().eventLoop().scheduleAtFixedRate(new WebsocketRunnable(ctx, messageRequest), 0, 10, TimeUnit.SECONDS);
                //存储客户端和服务的通信的Chanel
                channelMap.put(key, ctx.channel());
                futureMap.put(key, future);
            } else {
                //每次客户端和服务的主动通信，和服务端周期向客户端推送消息互不影响 解决问题一
                ctx.channel().writeAndFlush(new TextWebSocketFrame(Thread.currentThread().getName() + "服务器时间" + LocalDateTime.now() + "wdy"));
            }

        } catch (Exception e) {

            log.error("websocket服务器推送消息发生错误：", e);

        }
    }

    /**
     * 客户端连接时候的操作
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("客户端建立连接，channel对应的长id为：" + currentChannelId);
        // 获得客户端的channel，并且存入到ChannelGroup中进行管理(作为一个客户端群组)
        clients.add(currentChannel);
        log.info("一个客户端连接......" + ctx.channel().remoteAddress() + Thread.currentThread().getName());
    }

    /**
     * 客户端掉线时的操作
     * @param ctx
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("一个客户端移除......" + ctx.channel().remoteAddress());
        ctx.close(); //关闭连接
    }

    /**
     * 发生异常时执行的操作
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭长连接
        ctx.close();
        log.info("异常发生 " + cause.getMessage());
    }
}
