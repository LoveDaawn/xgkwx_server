package com.yuxi.xgkwx.domain.inventorymng;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CharacterPortrait {
    private Long id;
    private String characterName;
    private String portrait;
    private String insertTime;
    private String updateTime;
}
