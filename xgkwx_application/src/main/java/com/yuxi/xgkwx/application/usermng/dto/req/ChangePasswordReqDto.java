package com.yuxi.xgkwx.application.usermng.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChangePasswordReqDto {
    @NotNull
    private String unifyId;
    private String oldPassword;
    @NotBlank
    private String newPassword;
}
