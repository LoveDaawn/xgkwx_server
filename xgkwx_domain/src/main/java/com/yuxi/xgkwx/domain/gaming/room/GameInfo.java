package com.yuxi.xgkwx.domain.gaming.room;

import com.yuxi.xgkwx.domain.gaming.player.PlayerCardsVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Data
@Accessors(chain = true)
public class GameInfo {
    private short cardRemain;
    private Map<String, PlayerCardsVo> playerCardsMap;
    private int[] cards = new int[84];

    private static final Short CARD_INIT_NUM = 84 - 13 * 3;

    public GameInfo() {
    }

    /**
     * 初始化
     */
    public void init() {
        cardRemain = CARD_INIT_NUM; //初始化剩余张数
        cardInitAndShuffle(cards); //洗牌

    }

    private void cardInitAndShuffle(int[] cards) {
        for (int i = 0, j = 10; i < cards.length; i++) {
            if(i % 4 == 0) j++;
            if(j == 20 || j == 30 || j == 32 || j == 34) j++;
            cards[i] = j;
        }
        for (int i = 0; i < cards.length; i++) {
            int iRandNum = (int) (Math.random() * cards.length);
            int temp = cards[iRandNum];
            cards[iRandNum] = cards[i];
            cards[i] = temp;
        }
    }
}
