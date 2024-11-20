package com.yuxi.xgkwx.socket.msg;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
public class MessageRequest {
    private Long unionId;
    private String messageType;
    private String username;
}
