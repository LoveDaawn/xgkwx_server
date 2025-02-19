package com.yuxi.xgkwx.domain.gaming.room;

import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.exception.GameExceptionEnums;
import com.yuxi.xgkwx.domain.gaming.player.PlayerCardsVo;
import com.yuxi.xgkwx.domain.gaming.player.PlayerChannelVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 线程不安全，只允许单线程调用
 */
@Data
@Accessors(chain = true)
public class GameInfo {
    private short cardRemain;
    private Map<String, PlayerCardsVo> playerCardsMap;
    private short[] cards;
    int currentPointer = 0;

    private static final Short CARD_INIT_NUM = 84 - 13 * 3;

    public GameInfo() {
    }

    /**
     * 初始化
     */
    public void init(List<PlayerChannelVo> players) {
        //memory allocate
        cards = new short[84];
        playerCardsMap = new HashMap<>();
        //洗牌
        cardInitAndShuffle(cards);
        //发牌
        short[][] cardHeap = new short[3][40];
        for(int i = 0; i < 3; i++) {
            dealCard(cardHeap[0], 4);
            dealCard(cardHeap[1], 4);
            dealCard(cardHeap[2], 4);
        }
        dealCard(cardHeap[0],1);
        dealCard(cardHeap[1],1);
        dealCard(cardHeap[2],1);
        //分配玩家
        int k = 0;
        for(PlayerChannelVo player : players) {
            if(k < 3) playerCardsMap.put(player.getUnifyId(), new PlayerCardsVo(cardHeap[k++]));
        }
        //初始化剩余张数
        cardRemain = CARD_INIT_NUM;
    }


    /**
     * 牌组初始化 + 洗牌
     * @param cards：牌组
     */
    private void cardInitAndShuffle(short[] cards) {
        short j = 10;
        for (int i = 0; i < cards.length; i++) {
            if(i % 4 == 0) j++;
            if(j == 20 || j == 30 || j == 32 || j == 34) j++;
            cards[i] = j;
        }
        for (int i = 0; i < cards.length; i++) {
            int iRandNum = (int) (Math.random() * cards.length);
            short temp = cards[iRandNum];
            cards[iRandNum] = cards[i];
            cards[i] = temp;
        }
    }

    /**
     * 玩家进牌
     * @param unifyId 玩家id
     * @return 进的牌
     */
    public String cardIn(String unifyId) {
        return dealSingleCard(playerCardsMap.get(unifyId).getPlayerHandCards());
    }

    /**
     * 给玩家玩家手牌发1张牌
     * @param playerCard 玩家的手牌
     * @return 进的牌
     */
    private String dealSingleCard(short[] playerCard) {
        short card = cards[currentPointer++];
        playerCard[card]++;
        return String.valueOf(card);
    }

    /**
     * 给玩家玩家手牌发num张牌
     * @param playerCard 玩家手牌
     * @param num 发牌张数
     */
    private void dealCard(short[] playerCard, int num) {
        if(num == 0) return;
        for(int i = 0; i < num; i++) {
            playerCard[cards[currentPointer++]]++;
        }
    }

    /**
     * 玩家出牌
     * @param unifyId 玩家id
     * @param card 牌
     */
    public void cardOut(String unifyId, String card) {
        short[] cards = playerCardsMap.get(unifyId).getPlayerHandCards();
        short targetCard = Short.parseShort(card);
        if(cards[targetCard] > 0) cards[targetCard]--;
        else if (cards[targetCard] == -1) cards[targetCard] =  -3;
        else throw new CommonException(GameExceptionEnums.INNER_ERROR);
    }

    public void peng(String unifyId, String card) {
        short[] cards = playerCardsMap.get(unifyId).getPlayerHandCards();
        short targetCard = Short.parseShort(card);
        cards[targetCard] = -3;
    }

    public void gang(String unifyId, String card, String gangType) {
        short[] cards = playerCardsMap.get(unifyId).getPlayerHandCards();
        short targetCard = Short.parseShort(card);
        cards[targetCard] = -4;
    }
}
