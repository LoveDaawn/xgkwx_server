package com.yuxi.xgkwx.socket.msg.res.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class WaitOpContent {
    private boolean PENG;
    private boolean GANG;
    private boolean WIN;
    private String card;
    private String timeWait;
}
