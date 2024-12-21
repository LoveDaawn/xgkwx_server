package com.yuxi.xgkwx.socket.msg.res;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class MessageResponse<T> {
    private String code;
    private String msg;
    private T responseContent;

    public MessageResponse() {}

    public MessageResponse (T t) {
        this.responseContent = t;
    }
}
