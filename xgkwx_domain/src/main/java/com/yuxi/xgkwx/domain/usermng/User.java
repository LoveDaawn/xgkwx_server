package com.yuxi.xgkwx.domain.usermng;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class User {
    private Long userId;
    private String username;
    private String unifyId;
    private String password;
    private String email;
    private String phone;
    private String nickname;
    private Integer ranking;
    private Integer coin;
    private Integer voucher;
    private String memberStatus;
    private String memberExpire;
    private String insertTime;
    private String updateTime;
}
