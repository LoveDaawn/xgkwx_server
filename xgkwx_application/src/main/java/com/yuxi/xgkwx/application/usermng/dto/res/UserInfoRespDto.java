package com.yuxi.xgkwx.application.usermng.dto.res;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoRespDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private Integer ranking;
    private Integer coin;
    private Integer voucher;
}
