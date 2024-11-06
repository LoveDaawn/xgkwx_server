package com.yuxi.xgkwx.domain.gamemng;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GameRecord {
    private Long id;
    private String user1;
    private String user2;
    private String user3;
    private String gameRecord;
    private String insertTime;
    private String updateTime;
}
