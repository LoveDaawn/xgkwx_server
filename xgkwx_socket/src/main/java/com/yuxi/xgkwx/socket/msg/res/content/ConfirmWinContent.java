package com.yuxi.xgkwx.socket.msg.res.content;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ConfirmWinContent {
    private String unifyid;
    private String card;
    private List<String> winType;
    private Integer multiple;
}
