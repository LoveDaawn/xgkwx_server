package com.yuxi.xgkwx.socket.msg.req;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MessageRequest implements Serializable {
    private String userId;
    private String messageType;
    private String content;
    private String sendTime;
}
