package com.yuxi.xgkwx.socket.room;

import com.alibaba.fastjson.JSONObject;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.msg.res.MessageResponse;
import com.yuxi.xgkwx.socket.msg.res.room.CreateRoomMsgRes;
import com.yuxi.xgkwx.socket.msg.res.room.JoinRoomMsgRes;
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
        log.info("{}创建房间...", messageRequest.getUnifyId());
        //创建房间
        MessageResponse<CreateRoomMsgRes> mr = roomManager.createRoom(ctx.channel(), messageRequest);
        //回送消息
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
        // 离开房间
        MessageResponse<Void> mr = roomManager.leaveRoom(messageRequest);
        // 回送消息
        ctx.channel().writeAndFlush(JSONObject.toJSONString(mr));
    }

    public void prepare(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        log.info("{}玩家准备...", messageRequest.getUnifyId());
        // 离开房间
        MessageResponse<Void> mr = roomManager.prepare(messageRequest);
        // 回送消息
        ctx.channel().writeAndFlush(JSONObject.toJSONString(mr));
    }

    public void cancelPrepare(ChannelHandlerContext ctx, MessageRequest messageRequest) {
        log.info("{}玩家取消准备...", messageRequest.getUnifyId());
        // 离开房间
        MessageResponse<Void> mr = roomManager.cancelPrepare(messageRequest);
        // 回送消息
        ctx.channel().writeAndFlush(JSONObject.toJSONString(mr));
    }
}
