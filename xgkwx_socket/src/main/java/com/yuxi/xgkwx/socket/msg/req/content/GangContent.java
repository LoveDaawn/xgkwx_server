package com.yuxi.xgkwx.socket.msg.req.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GangContent {
    private String card;
    private String cardOutUnifyId;
    private String gangType;
}
