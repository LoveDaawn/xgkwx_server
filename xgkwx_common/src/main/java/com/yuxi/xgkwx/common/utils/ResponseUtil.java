package com.yuxi.xgkwx.common.utils;

import com.yuxi.xgkwx.common.enums.CommonResponseEnum;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.res.CommonResponse;

public class ResponseUtil {

    public static <T> CommonResponse<T> responseSuccess(T responseData) {
        CommonResponse<T> res = new CommonResponse<>(responseData);
        res.setCode(CommonResponseEnum.SUCCESS.getCode());
        res.setMessage(CommonResponseEnum.SUCCESS.getMsg());
        return res;
    }


    public static <T> CommonResponse<T> responseBusinessError(CommonException ce) {
        CommonResponse<T> res = new CommonResponse<>();
        res.setSuccess(false);
        res.setCode(ce.getCode());
        res.setMessage(ce.getMessage());
        return res;
    }

    public static <T> CommonResponse<T> responseError() {
        CommonResponse<T> res = new CommonResponse<>();
        res.setSuccess(false);
        res.setCode(CommonResponseEnum.SERVER_ERROR.getCode());
        res.setMessage(CommonResponseEnum.SERVER_ERROR.getMsg());
        return res;
    }

}
