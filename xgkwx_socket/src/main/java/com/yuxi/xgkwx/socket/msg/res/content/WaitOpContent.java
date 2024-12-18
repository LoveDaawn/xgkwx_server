package com.yuxi.xgkwx.socket.msg.res.content;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WaitOpContent {
    private boolean OUT;
    private boolean PENG;
    private boolean GANG;
    private boolean WIN;
    private String timeWait;
}
