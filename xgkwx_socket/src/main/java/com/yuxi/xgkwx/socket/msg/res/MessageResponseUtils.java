package com.yuxi.xgkwx.socket.msg.res;

import com.yuxi.xgkwx.common.enums.CommonResponseEnum;

public class MessageResponseUtils {

    public static <T> MessageResponse<T> responseSuccess(T responseData) {
        MessageResponse<T> res = new MessageResponse<>(responseData);
        res.setCode(CommonResponseEnum.SUCCESS.getCode());
        res.setMsg(CommonResponseEnum.SUCCESS.getMsg());
        return res;
    }

    public static MessageResponse<Void> responseSuccess() {
        MessageResponse<Void> res = new MessageResponse<>();
        res.setCode(CommonResponseEnum.SUCCESS.getCode());
        res.setMsg(CommonResponseEnum.SUCCESS.getMsg());
        return res;
    }
}
