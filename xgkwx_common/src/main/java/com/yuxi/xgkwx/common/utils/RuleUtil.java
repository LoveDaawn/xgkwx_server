package com.yuxi.xgkwx.common.utils;

import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.exception.GameExceptionEnums;

import java.util.ArrayList;
import java.util.List;

public class RuleUtil {
    /**
     * 是否是七对
     *
     * @param cards 手牌卡组
     * @return 龙七对龙数
     */
    public static int isQd(short[] cards) {
        int flag = 0;
        for (short c : cards) {
            if (c != 0 && c != 2 && c != 4) {
                return 0;
            }
            if (c == 4) {
                flag++;
            }
        }
        return flag;
    }


    /**
     * 是否是七对
     *
     * @param cards 手牌卡组
     * @return 龙七对龙数
     */
    public static int isQd(short[] cards, short card) {
        cardIn(cards, card);
        int flag = 0;
        for (short c : cards) {
            if (c != 0 && c != 2 && c != 4) {
                cardOut(cards, card);
                return 0;
            }
            if (c == 4) {
                flag++;
            }
        }
        cardOut(cards, card);
        return flag;
    }

    /**
     * 清一色判断
     */
    public static boolean isQys(short[] cards, short card) {
        boolean flagP = true; //筒(饼)一色
        boolean flagS = true; //条(索)一色
        //检测是否条一色
        int pointer = 11;
        while (pointer <= 35) {
            //有任何中/发/白
            if (pointer > 30 && (cards[pointer] > 0 || cards[pointer] == -1)) {
                return false;
            }
            //有任何非筒的牌
            if (pointer > 20 && (cards[pointer] > 0 || cards[pointer] == -1)) {
                flagP = false;
            }
            if (pointer > 10 && pointer < 20 && (cards[pointer] > 0 || cards[pointer] == -1)) {
                flagS = false;
            }
            pointer++;
        }
        return flagP || flagS;
    }

    /**
     * @param cards 手牌卡组
     * @return k重暗四归一
     */
    public static int checkAsgy(short[] cards, short card) {
        int flag = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == 4) {
                cards[i] -= 3;
                if (winCheck(cards, false)) flag++;
                cards[i] += 3;
            }
        }
        return flag;
    }

    /**
     * @param cards 手牌卡组
     * @param card  进牌
     * @return k重明四归一
     */
    public static int checkMsgy(short[] cards, short card) {
        int flag = 0;
        if(cards[card] == -3) flag++;
        if (isQys(cards, card)) {
            for (short c : cards) {
                if (c == -1) flag++;
            }
        }
        return flag;
    }

    /**
     * 碰碰胡判断
     */
    public static boolean isPph(short[] cards, short card) {
        int flag = 0;
        cardIn(cards, card);
        for (short c : cards) {
            if(c != 2 && c != 3 && c != -3 && c != -4) {
                cardOut(cards, card);
                return false;
            } else if(c == 2) {
                flag++;
            }
            if(flag > 1) {
                cardOut(cards, card);
                return false;
            }
        }
        cardOut(cards, card);
        return true;
    }


    /**
     * 卡五星判断
     */
    public static boolean isKwx(short[] cards, short card) {
        if(card != 15  && card != 25) {
            return false;
        }
        boolean flag;
        if (card == 15 && cards[14] > 0 && cards[16] > 0) {
            cardOut(cards, (short) 14);
            cardOut(cards, (short) 16);
            flag = winCheck(cards, false);
            cardIn(cards, (short) 14);
            cardIn(cards, (short) 16);
            return flag;
        }
        if (card == 25 && cards[24] > 0 && cards[26] > 0) {
            cardOut(cards, (short) 24);
            cardOut(cards, (short) 26);
            flag = winCheck(cards, false);
            cardIn(cards, (short) 24);
            cardIn(cards, (short) 26);
            return flag;
        }
        return false;
    }

    /**
     * 手抓一判断
     */
    public static boolean isSzy(short[] cards, short card) {
        if(cards[card] != 1) return false;
        cardOut(cards, card);
        for(short c : cards) {
            if(c > 0 || c == -1){
                cardIn(cards, card);
                return false;
            }
        }
        cardIn(cards, card);
        return true;
    }

    public static boolean pengCheck(short[] cards, short card) {
        return cards[card] == 2;
    }

    public static boolean gangCheck(short[] cards, short card, boolean cardInFlag) {
        if (cardInFlag)
            return cards[card] == 3 || cards[card] == -1;
        else
            return cards[card] == 3;
    }

    /**
     * 听牌判断，如果无法听牌，返回空数组
     */
    public static List<Integer> listenCheck() {

        return new ArrayList<>();
    }

    /**
     * 是否可以胡牌
     *
     * @param cards 手牌卡组
     * @param card  目标牌
     * @return 是否可以胡牌
     */
    public static boolean winCheck(short[] cards, short card) {
        cards[card]++;
        boolean flag = winCheck(cards, true);
        cards[card]--;
        if (flag) {

        }
        return flag;
    }

    /**
     * 是否已经胡牌
     *
     * @param cards    手牌卡组
     * @param fullFlag 手牌是否满14张, 默认传true, 只有确定为否的时候传false
     * @return 是否已经胡牌
     */
    public static boolean winCheck(short[] cards, boolean fullFlag) {
        if (fullFlag && isQd(cards) > 0) {
            return true;
        }
        boolean isWin = false;
        for (int i = 11; i < 36; i++) {
            if (cards[i] >= 2) { //找将
                cards[i] -= 2;  //i位置可以作为将
                isWin = backtrack(cards); //递归
                cards[i] += 2; //恢复现场
            }
            if (isWin) break;
        }
        return isWin;
    }

    /**
     * 进入递归方法的手牌已经被处理完将牌了，因此只需判断坎和顺子即可
     *
     * @param cards 手牌卡组
     * @return 递归处理-是否胡牌
     */
    private static boolean backtrack(short[] cards) {
        if (isAllLQZero(cards)) { //递归出口，是否已经胡牌
            return true;
        }
        boolean isWin = false;
        for (int i = 11; i < 36; i++) {
            if (cards[i] >= 3) { //是否可以组成坎
                cards[i] -= 3;
                isWin = backtrack(cards);
                cards[i] += 3; //恢复现场
            }
            if (isWin) break;
            if (cards[i] > 0 || cards[i] == -1) { //这个位置是否有手牌
                if ((cards[i + 1] > 0 || cards[i + 1] == -1) && (cards[i + 2] > 0 || cards[i + 2] == -1)) { //是否有顺子
                    cards[i]--;
                    cards[i + 1]--;
                    cards[i + 2]--;
                    isWin = backtrack(cards);
                    cards[i]++;
                    cards[i + 1]++;
                    cards[i + 2]++; //恢复现场
                } else { //剪枝
                    return false;
                }
            }
            if (isWin) break;
        }
        return isWin;
    }

    private static boolean isAllLQZero(short[] cards) {
        for (int card : cards) {
            if (card > 0 || card == -1) {
                return false;
            }
        }
        return true;
    }


    public static int calculateMultiple(short[] card, boolean gangTimes) {
        return 0;
    }

    public static int calculateExtraScore(short[] card, short[] szArray) {
        return 0;
    }


    public static int calculateScore() {
        return 0;
    }

    /**
     * 给手牌发牌
     */
    public static void cardIn(short[] cards, short card) {
        if (cards[card] != -3) {
            cards[card]++;
        } else {
            cards[card] = -1;
        }
    }

    /**
     * 打牌
     */
    public static void cardOut(short[] cards, short card) {

    }

    public static void cardGang(short[] cards, short card) {
        if (cards[card] != 3 && cards[card] != -3) {
            throw new CommonException(GameExceptionEnums.SERVER_ERROR);
        } else {
            cards[card] = -4;
        }
    }


}
