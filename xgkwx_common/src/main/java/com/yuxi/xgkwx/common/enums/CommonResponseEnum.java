package com.yuxi.xgkwx.common.enums;

import lombok.Getter;

@Getter
public enum CommonResponseEnum {
    SUCCESS("200", "成功"),
    REQUEST_ERROR("400", "请求错误"),
    SERVER_ERROR("500", "服务异常");

    private final String code;
    private final String msg;

    CommonResponseEnum (String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
