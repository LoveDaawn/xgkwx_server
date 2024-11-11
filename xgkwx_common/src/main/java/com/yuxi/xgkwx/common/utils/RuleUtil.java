package com.yuxi.xgkwx.common.utils;

public class RuleUtil {
    public static boolean isWin(int[] cards) {
        if(isSevenPairs(cards)) {
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

    public static boolean isSevenPairs(int[] cards) {
        for (int card : cards) {
            if (card != 0 && card != 2) {
                return false;
            }
        }
        return true;
    }

    /**
     * 进入递归方法的手牌已经被处理完将牌了，因此只需判断坎和顺子即可
     * @param cards
     * @return
     */
    public static boolean backtrack(int[] cards) {
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

    public static boolean isAllLQZero(int[] cards) {
        for (int card : cards) {
            if (card > 0 || card == -1) {
                return false;
            }
        }
        return true;
    }
}
