package com.yuxi.xgkwx.socket.msg.res.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardInContent {
    private String info;
    private boolean gang;
    private boolean win;
    private String timeWait;
}
