package com.yuxi.xgkwx.domain.gaming.room;

import com.yuxi.xgkwx.domain.gaming.player.PlayerChannelVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class RoomVo {
    private String roomId;
    private List<PlayerChannelVo> players;
    private String roomType;
    private Map<String, String> rules;
    private String roomMaster;
    private GameInfo gameInfo;
    private boolean gameStart = false;
}
