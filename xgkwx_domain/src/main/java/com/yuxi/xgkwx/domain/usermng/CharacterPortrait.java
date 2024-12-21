package com.yuxi.xgkwx.domain.usermng;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CharacterPortrait {
    private Long characterPortraitId;
    private String characterName;
    private String baseFlag;
    private String portrait;
    private String insertTime;
    private String updateTime;
}
