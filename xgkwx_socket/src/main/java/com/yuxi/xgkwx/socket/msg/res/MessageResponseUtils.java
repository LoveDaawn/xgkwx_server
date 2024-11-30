package com.yuxi.xgkwx.socket.msg.res;

import com.yuxi.xgkwx.common.enums.CommonResponseEnum;

public class MessageResponseUtils {


    public static <T> MessageResponse<T> responsSuccess(T responseData) {
        MessageResponse<T> res = new MessageResponse<>(responseData);
        res.setCode(CommonResponseEnum.SUCCESS.getCode());
        res.setMsg(CommonResponseEnum.SUCCESS.getMsg());
        return res;
    }
}
