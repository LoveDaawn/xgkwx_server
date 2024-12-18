package com.yuxi.xgkwx.socket.msg.req.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OtherPlayerInContent {
    private String unifyId;
    private String timeWait;
}
