package com.yuxi.xgkwx.common.enums;

import lombok.Getter;

@Getter
public enum CommonResponseEnum {
    SUCCESS("0000", "成功");

    private String code;
    private String msg;

    CommonResponseEnum (String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
