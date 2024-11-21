package com.yuxi.xgkwx.socket.msg.req.room;

import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import lombok.Getter;

import java.util.Map;

@Getter
public class CreateRoomMsgReq {
    private Integer playersNum;
    private String nickname;
    private Map<String, String> rules;
}
