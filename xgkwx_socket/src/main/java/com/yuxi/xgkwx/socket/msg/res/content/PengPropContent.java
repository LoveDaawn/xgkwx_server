package com.yuxi.xgkwx.socket.msg.res.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PengPropContent {
    private String unifyId;
    private String card;
    private String cardOutUnifyId;
}
