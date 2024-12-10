package com.yuxi.xgkwx.application.usermng.dto.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteUserReqDto {
    private String unifyId;
    private String password;
}
