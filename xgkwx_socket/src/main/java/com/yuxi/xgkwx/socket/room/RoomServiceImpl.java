package com.yuxi.xgkwx.socket.room;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.yuxi.xgkwx.socket.msg.req.content.CreateRoomContent;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.msg.req.content.JoinRoomContent;
import com.yuxi.xgkwx.socket.msg.res.MessageResponse;
import com.yuxi.xgkwx.socket.msg.res.MessageResponseUtils;
import com.yuxi.xgkwx.socket.msg.res.room.CreateRoomMsgRes;
import com.yuxi.xgkwx.socket.msg.res.room.JoinRoomMsgRes;
import com.yuxi.xgkwx.socket.websocket.WebSocketHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RoomServiceImpl {

    private final static Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    @Resource
    private RoomManager roomManager;

    public void createRoom(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        log.info("{}创建房间...", messageRequest.getUnifyId());
        //创建房间
        String roomId = roomManager.createRoom(ctx.channel(), messageRequest);
        CreateRoomMsgRes crm = new CreateRoomMsgRes(roomId);
        //回送消息
        MessageResponse<CreateRoomMsgRes> mr = MessageResponseUtils.responsSuccess(crm);
        ctx.channel().writeAndFlush(JSONObject.toJSONString(mr));
    }

    public void joinRoom(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        log.info("{}加入房间...", messageRequest.getUnifyId());
        //加入房间
        MessageResponse<JoinRoomMsgRes> mr = roomManager.joinRoom(ctx.channel(), messageRequest);
        //回送消息
        ctx.channel().writeAndFlush(JSONObject.toJSONString(mr));
    }

    public void leaveRoom(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        log.info("{}离开房间...", messageRequest.getUnifyId());
    }
}
