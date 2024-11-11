package com.yuxi.xgkwx.common.res;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class CommonResponse<T> {
    private boolean isSuccess = true;
    private String code;
    private String message;
    private T responseData;

    public CommonResponse() {
    }

    public CommonResponse(T responseData) {
        this.responseData = responseData;
    }
}
