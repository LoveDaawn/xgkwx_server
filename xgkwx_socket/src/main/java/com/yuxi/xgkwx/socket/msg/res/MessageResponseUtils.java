package com.yuxi.xgkwx.socket.msg.res;

import com.yuxi.xgkwx.common.enums.CommonResponseEnum;
import com.yuxi.xgkwx.common.exception.GameExceptionEnums;

public class MessageResponseUtils {

    public static <T> MessageResponse<T> responseSuccess(T responseData, String ack) {
        MessageResponse<T> res = new MessageResponse<>(responseData);
        res.setAck(ack);
        res.setCode(CommonResponseEnum.SUCCESS.getCode());
        res.setMsg(CommonResponseEnum.SUCCESS.getMsg());
        return res;
    }

    public static MessageResponse<Void> responseSuccess(String ack) {
        MessageResponse<Void> res = new MessageResponse<>();
        res.setAck(ack);
        res.setCode(CommonResponseEnum.SUCCESS.getCode());
        res.setMsg(CommonResponseEnum.SUCCESS.getMsg());
        return res;
    }

    public static MessageResponse<Void> responseFail(GameExceptionEnums gee, String ack) {
        MessageResponse<Void> res = new MessageResponse<>();
        res.setAck(ack);
        res.setCode(gee.getCode());
        res.setMsg(gee.getMsg());
        return res;
    }
}
