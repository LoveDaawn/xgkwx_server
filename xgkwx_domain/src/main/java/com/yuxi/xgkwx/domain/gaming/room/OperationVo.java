package com.yuxi.xgkwx.domain.gaming.room;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationVo {
    private String unifyId;
    private String operation;
    private String card;
    private String gangType;
}
