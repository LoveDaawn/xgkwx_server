package com.yuxi.xgkwx.application.usermng.dto.req;

import lombok.Data;

@Data
public class UserRegisterReqDto {
    private String unifyId;
    private String phone;
    private String nickname;
}
