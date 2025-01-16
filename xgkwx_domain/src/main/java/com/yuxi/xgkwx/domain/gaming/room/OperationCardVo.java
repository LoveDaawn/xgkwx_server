package com.yuxi.xgkwx.domain.gaming.room;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationCardVo {
    private String unifyId;
    private Short card;
    private String operationType;
}
