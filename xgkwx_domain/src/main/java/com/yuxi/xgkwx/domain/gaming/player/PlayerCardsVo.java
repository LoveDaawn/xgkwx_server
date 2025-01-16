package com.yuxi.xgkwx.domain.gaming.player;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 玩家手牌
 */
@Data
@Accessors(chain = true)
public class PlayerCardsVo {
    private short[] playerHandCards;
    private List<String> playerOutsCardList;
    private Map<String, String> listenMap;
    private boolean ldFlag = false;

    public PlayerCardsVo(short[] playerHandCards) {
        this.playerHandCards = playerHandCards;
        this.playerOutsCardList = new ArrayList<>();
        this.listenMap = new HashMap<>();
    }
}
