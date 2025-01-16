package com.yuxi.xgkwx.common.rule.enums;


import lombok.Getter;

@Getter
public enum OperationTypeEnum {
    IN("IN", "进牌"),
    OUT("OUT", "出牌"),
    PENG("PENG", "碰"),
    GANG("GANG", "炮杠"),
    AN_GANG("AN_GANG", "暗杠"),
    CA_GANG("CA_GANG", "擦杠"),
    WIN("WIN", "胡"),
    ;

    private String code;
    private String desc;

    OperationTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
