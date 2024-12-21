package com.yuxi.xgkwx.socket.msg.res.room;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class JoinRoomMsgRes {
    private Map<String, String> rules;
}
