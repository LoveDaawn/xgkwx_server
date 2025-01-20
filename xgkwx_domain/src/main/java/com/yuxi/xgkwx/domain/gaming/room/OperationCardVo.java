package com.yuxi.xgkwx.domain.gaming.room;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class OperationCardVo {
    private String unifyId;
    private Short card;
    private List<String> operationTypeList;

    public OperationCardVo(String unifyId, Short card, String operationType) {
        this.unifyId = unifyId;
        this.card = card;
        this.operationTypeList = new ArrayList<>();
        this.operationTypeList.add(operationType);
    }
}
