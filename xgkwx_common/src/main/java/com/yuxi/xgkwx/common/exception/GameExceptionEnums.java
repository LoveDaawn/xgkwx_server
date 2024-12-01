package com.yuxi.xgkwx.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameExceptionEnums {
    SERVER_ERROR("E10000", "系统异常"),
    MESSAGE_TYPE_ERROR("E10001", "房间数已满"),
    FIND_ROOM_ERROR("E10002", "找不到房间"),
    ROOM_FULL("E10003", "房间人数已满"),
    ;

    private String code;
    private String msg;
}
