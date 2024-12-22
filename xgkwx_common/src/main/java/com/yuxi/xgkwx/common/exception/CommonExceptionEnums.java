package com.yuxi.xgkwx.common.exception;

import lombok.Getter;

@Getter
public enum CommonExceptionEnums {

    SERVER_ERROR("E00000", "系统异常"),
    MESSAGE_TYPE_ERROR("E00001", "消息类型错误")
    ;

    private String code;
    private String msg;

    CommonExceptionEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
