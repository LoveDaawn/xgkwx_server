package com.yuxi.xgkwx.socket.room;

import com.alibaba.fastjson2.JSON;
import com.yuxi.xgkwx.common.utils.GameUtils;
import com.yuxi.xgkwx.domain.gaming.room.RoomVo;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.msg.req.content.CreateRoomContent;
import com.yuxi.xgkwx.socket.msg.req.content.JoinRoomContent;
import com.yuxi.xgkwx.socket.msg.res.MessageResponse;
import com.yuxi.xgkwx.socket.msg.res.MessageResponseUtils;
import com.yuxi.xgkwx.socket.msg.res.room.JoinRoomMsgRes;
import io.netty.channel.Channel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class RoomManager {
    // 用户管理所有房间
    private static volatile Map<String, RoomVo> roomManagerChannelMap;

    @PostConstruct
    public void init() {
        roomManagerChannelMap = new ConcurrentHashMap<>();

    }

    /**
     * 创建房间，
     * @param channel 通信channel
     * @return 房间号id
     */
    public String createRoom(Channel channel, MessageRequest messageRequest) {
        CreateRoomContent content = JSON.parseObject(messageRequest.getContent(), CreateRoomContent.class);
        RoomVo roomVo = new RoomVo();
        List<Channel> roomConn = new CopyOnWriteArrayList<>(); //线程安全list
        List<String> players = new CopyOnWriteArrayList<>();
        players.add(messageRequest.getUnifyId());
        roomConn.add(channel);
        String roomId = GameUtils.genRoomId();
        roomVo.setRoomId(roomId)
                .setRoomConn(roomConn)
                .setPlayers(players)
                .setRules(content.getRules());
        roomManagerChannelMap.put(roomId, roomVo);
        return roomId;
    }

    /**
     * 创建房间，
     * @param channel 通信channel
     * @return 房间号id
     */
    public MessageResponse<JoinRoomMsgRes> joinRoom(Channel channel, MessageRequest messageRequest) {
        JoinRoomContent content = JSON.parseObject(messageRequest.getContent(), JoinRoomContent.class);
        RoomVo roomVo = roomManagerChannelMap.get(content.getRoomId());
        roomVo.getRoomConn().add(channel);
        roomVo.getPlayers().add(messageRequest.getUnifyId());
        JoinRoomMsgRes r = new JoinRoomMsgRes(roomVo.getRules());
        return MessageResponseUtils.responsSuccess(r);
    }
}
