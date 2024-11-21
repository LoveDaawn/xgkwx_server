package com.yuxi.xgkwx.socket.room;

import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.exception.CommonExceptionEnum;
import com.yuxi.xgkwx.socket.msg.req.room.CreateRoomMsgReq;
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
//        log.info("{}创建房间...", messageRequest.getUsername());
//        CreateRoomMsgReq crm = (CreateRoomMsgReq) messageRequest;
//        roomManager.createRoom(crm.getRoomId(), ctx.channel());
    }

//    public void joinRoom(MessageRequest messageRequest) {
//        log.info("{}加入房间...", messageRequest.getUsername());
//    }

//    public void leaveRoom(MessageRequest messageRequest) {
//        log.info("{}离开房间...", messageRequest.getUsername());
//    }
}
