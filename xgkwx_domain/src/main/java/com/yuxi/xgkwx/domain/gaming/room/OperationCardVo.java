package com.yuxi.xgkwx.domain.gaming.room;

import com.yuxi.xgkwx.common.rule.enums.OperationTypeEnum;
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

    public boolean canPeng() {
        return operationTypeList.contains(OperationTypeEnum.PENG.getCode());
    }

    public boolean canGang() {
        return operationTypeList.contains(OperationTypeEnum.GANG.getCode());
    }

    public boolean canWin() {
        return operationTypeList.contains(OperationTypeEnum.WIN.getCode());
    }
}
