package com.yuxi.xgkwx.application.usermng.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginReqDto {
    @NotBlank
    private String unifyId;
    @NotBlank
    private String password;
}
