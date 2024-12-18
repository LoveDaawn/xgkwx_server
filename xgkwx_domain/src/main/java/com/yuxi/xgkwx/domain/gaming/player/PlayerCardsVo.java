package com.yuxi.xgkwx.domain.gaming.player;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家手牌
 */
@Data
@Accessors(chain = true)
public class PlayerCardsVo {
    private short[] playerHandCards;
    private List<String> playerOutsCardMap;

    public PlayerCardsVo(short[] playerHandCards) {
        this.playerHandCards = playerHandCards;
        this.playerOutsCardMap = new ArrayList<>();
    }
}
