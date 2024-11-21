package com.yuxi.xgkwx.domain.gaming.room;

import com.yuxi.xgkwx.domain.gaming.rule.RuleVO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RoomVO {
    private String roomId;
    private List<String> players;
    private String roomType;
    private RuleVO rule;
}
