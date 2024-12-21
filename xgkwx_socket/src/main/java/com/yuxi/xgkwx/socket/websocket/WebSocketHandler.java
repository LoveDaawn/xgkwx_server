package com.yuxi.xgkwx.socket.websocket;

import com.alibaba.fastjson.JSON;
import com.yuxi.xgkwx.common.enums.GameMsgEnums;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.room.RoomServiceImpl;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 自定义助手类
 * SimpleChannelInboundHandler: 对于请求来说，相当于入站(入境)
 * TextWebSocketFrame: 用于为websocket专门处理的文本数据对象，Frame是数据(消息)的载体
 */
@Component
@ChannelHandler.Sharable
public class WebSocketHandler extends SimpleChannelInboundHandler<String> {

    private final static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    // 用于记录和管理所有客户端的channel组
    public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Resource
    private RoomServiceImpl roomService;


    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        // 获得客户端传输过来的消息
        log.info("接受到的数据：{}", msg);
        try {
            // 1. 获取客户端发来的消息并且解析
            MessageRequest messageRequest = JSON.parseObject(msg, MessageRequest.class);
            GameMsgEnums msgType = GameMsgEnums.getGameMsgByCode(messageRequest.getMessageType());
            // 获取channel
            Channel currentChannel = ctx.channel();
            String currentChannelId = currentChannel.id().asLongText();
            String currentChannelIdShort = currentChannel.id().asShortText();
            log.info("客户端currentChannelId:{}", currentChannelId);
            log.info("客户端currentChannelIdShort:{}", currentChannelIdShort);

            // 2. 判断消息类型，根据不同的类型来处理不同的业务
            switch (Objects.requireNonNull(msgType)) {
                case CREATE_ROOM:
                    roomService.createRoom(ctx, messageRequest);
                    break;
                case JOIN_ROOM:
                    roomService.joinRoom(ctx, messageRequest);
                    break;
                case LEAVE_ROOM:
                    roomService.leaveRoom(ctx, messageRequest);
                    break;
                case PREPARE:
                    roomService.prepare(ctx, messageRequest);
                    break;
                case CANCEL_PREPARE:
                    roomService.cancelPrepare(ctx, messageRequest);
                    break;
                default:
                    throw new CommonException("500", "游戏服务异常");
                    //do nothing
            }
        } catch (CommonException e) {
            log.error("消息处理异常，code={}, info={}", e.getCode(), e.getMsg(), e);
        } catch (Throwable e) {
            log.error("系统异常", e);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * channelActive
     * 通道激活方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("server channel active..");
    }

    /**
     * 客户端连接到服务端之后(打开链接)
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        log.info("客户端建立连接，channel对应的长id为：{}", currentChannelId);
        // 获得客户端的channel，并且存入到ChannelGroup中进行管理(作为一个客户端群组)
        clients.add(currentChannel);
    }

    /**
     * 关闭连接，移除channel
     * @param ctx ChannelHandlerContext
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        log.info("客户端关闭连接，channel对应的长id为:{}", currentChannelId);
        clients.remove(currentChannel);
    }


    /**
     * 发生异常并且捕获，移除channel
     * @param ctx ChannelHandlerContext
     * @param cause 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel currentChannel = ctx.channel();
        String currentChannelId = currentChannel.id().asLongText();
        System.out.println("发生异常捕获，channel对应的长id为：" + currentChannelId);
        // 发生异常之后关闭连接(关闭channel)
        ctx.channel().close();
        // 随后从ChannelGroup中移除对应的channel
        clients.remove(currentChannel);
    }
}
