package com.yuxi.xgkwx.socket.msg.res.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GangPropContent {
    private String unifyId;
    private String card;
    private String gangType;
}
