package com.yuxi.xgkwx.common.utils;

import java.util.List;

public class RuleUtil {


    /**
     * 是否是七对
     * @param cards 手牌卡组
     * @return 是否是七对
     */
    public static boolean isQd(short[] cards) {
        for (short card : cards) {
            if (card != 0 && card != 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * 清一色判断
     */
    public static boolean isQys(short[] cards) {
        boolean flagP = true; //筒(饼)一色
        boolean flagS = true; //条(索)一色
        //检测是否条一色
        int pointer = 11;
        while(pointer <= 35) {
            //有任何中/发/白
            if(pointer > 30 && (cards[pointer] > 0 || cards[pointer] == -1)) {
                return false;
            }
            //有任何非筒的牌
            if(pointer > 20 && (cards[pointer] > 0 || cards[pointer] == -1)) {
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
     * 暗四归一判断
     */
    public static boolean isAsgy () {
        return false;
    }

    /**
     * 双暗四归一判断
     */
    public static boolean isDoubleAsgy () {
        return false;
    }

    /**
     * 明四归一判断
     */
    public static boolean isMsgy () {
        return false;
    }

    /**
     * 碰碰胡判断
     */
    public static boolean isPph () {
        return false;
    }

    /**
     * 抢杠判断
     */
    public static boolean isQg () {
        return false;
    }

    /**
     * 卡五星判断
     */
    public static boolean isKwx () {
        return false;
    }

    /**
     * 龙七对判断
     */
    public static boolean isLqd () {
        return false;
    }

    /**
     * 手抓一判断
     */
    public static boolean isSzy () {
        return false;
    }

    public static boolean pengCheck(short[] cards, short card) {
        return cards[card] == 2;
    }

    public static boolean gangCheck(short[] cards, short card, boolean cardInFlag) {
        if(cardInFlag)
            return cards[card] == 3 || cards[card] == -1;
        else
            return cards[card] == 3;
    }

    /**
     * 听牌判断，如果无法听牌，返回空数组
     */
//    public static List<Integer> listenCheck() {
//
//        return false;
//    }

    /**
     * 是否可以胡牌
     * @param cards 手牌卡组
     * @param card 目标牌
     * @return 是否可以胡牌
     */
    public static boolean winCheck(short[] cards, short card) {
        cards[card]++;
        boolean flag = winCheck(cards);
        cards[card]--;
        if(flag) {

        }
        return flag;
    }

    /**
     * 是否已经胡牌
     * @param cards 手牌卡组
     * @return 是否已经胡牌
     */
    public static boolean winCheck(short[] cards) {
        if(isQd(cards)) {
            return true;
        }
        boolean isWin = false;
        for(int i = 11; i < 36; i++) {
            if(cards[i] >= 2) { //找将
                cards[i] -= 2;  //i位置可以作为将
                isWin = backtrack(cards); //递归
                cards[i] += 2; //恢复现场
            }
            if(isWin) break;
        }
        return isWin;
    }

    /**
     * 进入递归方法的手牌已经被处理完将牌了，因此只需判断坎和顺子即可
     * @param cards 手牌卡组
     * @return 递归处理-是否胡牌
     */
    private static boolean backtrack(short[] cards) {
        if(isAllLQZero(cards)) { //递归出口，是否已经胡牌
            return true;
        }
        boolean isWin = false;
        for(int i = 11; i < 36; i++) {
            if(cards[i] >= 3) { //是否可以组成坎
                cards[i] -= 3;
                isWin = backtrack(cards);
                cards[i] += 3; //恢复现场
            }
            if(isWin) break;
            if(cards[i] > 0 || cards[i] == -1) { //这个位置是否有手牌
                if((cards[i+1] > 0 || cards[i+1] == -1) && (cards[i+2] > 0 || cards[i+2] == -1)) { //是否有顺子
                    cards[i]--; cards[i+1]--; cards[i+2]--;
                    isWin = backtrack(cards);
                    cards[i]++; cards[i+1]++; cards[i+2]++; //恢复现场
                } else { //剪枝
                    return false;
                }
            }
            if(isWin) break;
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


}
