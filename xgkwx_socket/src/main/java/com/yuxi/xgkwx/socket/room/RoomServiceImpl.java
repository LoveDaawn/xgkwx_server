package com.yuxi.xgkwx.socket.room;

import com.alibaba.fastjson2.JSON;
import com.yuxi.xgkwx.socket.msg.req.content.CreateRoomContent;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.websocket.WebSocketHandler;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl {
    private final static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    @Resource
    private RoomManager roomManager;

    public void createRoom(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        log.info("{}创建房间...", messageRequest.getUserId());
        CreateRoomContent content = JSON.parseObject(messageRequest.getContent(), CreateRoomContent.class);
//        roomManager.createRoom(crm.getRoomId(), ctx.channel());
    }

//    public void joinRoom(MessageRequest messageRequest) {
//        log.info("{}加入房间...", messageRequest.getUsername());
//    }

//    public void leaveRoom(MessageRequest messageRequest) {
//        log.info("{}离开房间...", messageRequest.getUsername());
//    }
}
