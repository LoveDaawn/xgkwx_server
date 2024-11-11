package com.yuxi.xgkwx.application.usermng.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String unifyId;
    private String phone;
    private String nickname;
    private Integer ranking;

}
