package com.yuxi.xgkwx.domain.inventorymng;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Character {
    private Long id;
    private String characterName;
    private String thumbnail;
    private String insertTime;
    private String updateTime;
}
