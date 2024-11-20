package com.yuxi.xgkwx.common.exception;

import lombok.Data;

@Data
public class CommonException extends RuntimeException{

    private String code;
    private String msg;

    public CommonException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonException(CommonExceptionEnum commonExceptionEnum) {
        this.code = commonExceptionEnum.getCode();
        this.msg = commonExceptionEnum.getMsg();
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
