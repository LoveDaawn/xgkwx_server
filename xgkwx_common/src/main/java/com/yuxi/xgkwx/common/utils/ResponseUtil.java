package com.yuxi.xgkwx.common.utils;

import com.yuxi.xgkwx.common.enums.CommonResponseEnum;
import com.yuxi.xgkwx.common.res.CommonResponse;

public class ResponseUtil {

    public static <T> CommonResponse<T> responsSuccess(T responseData) {
        CommonResponse<T> res = new CommonResponse<>(responseData);
        res.setCode(CommonResponseEnum.SUCCESS.getCode());
        res.setMessage(CommonResponseEnum.SUCCESS.getMsg());
        return res;
    }


    public static <T> CommonResponse<T> responsError(String code, String message) {
        CommonResponse<T> res = new CommonResponse<>();
        res.setSuccess(false);
        res.setCode(code);
        res.setMessage(message);
        return res;
    }

}
