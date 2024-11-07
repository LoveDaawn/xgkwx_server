package com.yuxi.xgkwx.domain.usermng;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Character {
    private Long characterId;
    private String characterName;
    private String thumbnail;
    private String insertTime;
    private String updateTime;
}
