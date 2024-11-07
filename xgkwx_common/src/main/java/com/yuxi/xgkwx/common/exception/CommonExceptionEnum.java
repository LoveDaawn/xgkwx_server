package com.yuxi.xgkwx.common.exception;

import lombok.Getter;

@Getter
public enum CommonExceptionEnum {

    SERVER_ERROR("E00000", "系统异常")
    ;

    private String code;
    private String msg;

    CommonExceptionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
