package com.yuxi.xgkwx.domain.gaming.player;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 玩家手牌
 */
@Data
@Accessors(chain = true)
public class PlayerCardsVo {
    private short[] playerHandCards;
    private List<String> playerOutsCardMap;
}
