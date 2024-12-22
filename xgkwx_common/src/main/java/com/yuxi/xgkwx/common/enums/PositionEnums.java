package com.yuxi.xgkwx.common.enums;

import lombok.Getter;

@Getter
public enum PositionEnums {
    EAST("east", "东"),
    SOUTH("south", "南"),
    NORTH("north", "北"),
    ;


    private String code;
    private String info;

    PositionEnums (String code, String info) {
        this.code = code;
        this.info = info;
    }
}
