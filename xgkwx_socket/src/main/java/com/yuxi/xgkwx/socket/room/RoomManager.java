package com.yuxi.xgkwx.socket.room;

import cn.hutool.core.collection.ConcurrentHashSet;
import io.netty.channel.Channel;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomManager {

    // 用户管理所有房间
    private Map<String, Set<Channel>> roomManagerMap;

    @PostConstruct
    public void init() {
        roomManagerMap = new ConcurrentHashMap<>();
    }

    public void createRoom(String roomId, Channel channel) {
        ConcurrentHashSet<Channel> roomConn = new ConcurrentHashSet<>();
        roomConn.add(channel);
        roomManagerMap.put(roomId, roomConn);
    }
}
