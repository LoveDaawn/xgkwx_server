package com.yuxi.xgkwx.socket.msg.res.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerJoinedContent {
    private String unifyId;
    private String position;
}
