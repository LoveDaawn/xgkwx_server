package com.yuxi.xgkwx.application.usermng.dto.req;

import lombok.Data;

@Data
public class DeleteUserReqDto {
    private String unifyId;
    private String password;
}
