package com.yuxi.xgkwx.socket.msg.req.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PengContent {
    private String card;
    private String cardOutUnifyId; //打牌者id
}
