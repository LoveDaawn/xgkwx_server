package com.yuxi.xgkwx.domain.gaming.room;

import cn.hutool.core.lang.hash.Hash;
import com.yuxi.xgkwx.common.enums.PositionEnums;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.exception.GameExceptionEnums;
import com.yuxi.xgkwx.domain.gaming.player.PlayerChannelVo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

@Data
@Accessors(chain = true)
public class RoomVo {
    private String roomId;
    private List<PlayerChannelVo> players; //约定，0位置为东，1位置为南，2位置为北
    private String roomType;
    private Map<String, String> rules;
    private String roomMaster; //房主
    private String banker; //庄家
    private GameInfo gameInfo;
    private boolean gameStart = false;
    private Map<String, List<String>> waitOrderMap;
    private Map<String, OperationVo> operationMap; //key: operation

    public PlayerChannelVo selectPlayer(String unifyId) {
        if (unifyId == null) return null;
        for (PlayerChannelVo playerChannelVo : players) {
            if (unifyId.equals(playerChannelVo.getUnifyId())) {
                return playerChannelVo;
            }
        }
        return null;
    }

    public int selectPlayerIndex(String unifyId) {
        if (!StringUtils.hasText(unifyId)) {
            return -1;
        }
        int index = 0;
        for (; index < players.size(); index++) {
            if (unifyId.equals(players.get(index).getUnifyId())) {
                return index;
            }
        }
        return -1;
    }

    public String getPositionByIndex(String unifyId) {
        int index = selectPlayerIndex(unifyId);
        if (index >= 0 && index < 3) {
            return getPositionByIndex(index);
        }
        return null;
    }

    public String getPositionByIndex(int index) {
        if (index == 0) return PositionEnums.EAST.getCode();
        else if (index == 1) return PositionEnums.SOUTH.getCode();
        else if (index == 2) return PositionEnums.NORTH.getCode();
        else throw new CommonException(GameExceptionEnums.SERVER_ERROR);
    }

    public Map<String, String> buildPlayersInfo() {
        Map<String, String> playersInfo = new HashMap<>();
        if (players == null || players.isEmpty()) {
            return playersInfo;
        }
        for (int i = 0; i < players.size(); i++) {
            playersInfo.put(getPositionByIndex(i), players.get(i).getUnifyId());
        }
        return playersInfo;
    }
}
