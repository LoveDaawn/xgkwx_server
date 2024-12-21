package com.yuxi.xgkwx.application.usermng.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateReqDto {
    @NotNull
    private String unifyId;
    private String nickname;
    private String email;
    private String phone;
}

