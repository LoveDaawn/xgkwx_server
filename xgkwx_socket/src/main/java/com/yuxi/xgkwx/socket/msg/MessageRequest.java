package com.yuxi.xgkwx.socket.msg;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageRequest {
    private Long unionId;

}
