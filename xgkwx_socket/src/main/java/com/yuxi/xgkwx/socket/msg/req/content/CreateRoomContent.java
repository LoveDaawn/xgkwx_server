package com.yuxi.xgkwx.socket.msg.req.content;

import lombok.Data;

import java.util.Map;

@Data
public class
CreateRoomContent {
    private Integer playersNum;
    private String nickName;
    private Map<String, String> rules;
}
