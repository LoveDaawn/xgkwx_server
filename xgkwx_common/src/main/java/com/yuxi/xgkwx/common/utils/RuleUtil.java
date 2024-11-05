package com.yuxi.xgkwx.common.utils;

public class RuleUtil {
    public static boolean isWin(int[] cards) {
        if(isSevenPairs(cards)) {
            return true;
        }
        boolean isWin = false;
        for(int i = 0; i < 36; i++) {
            if(cards[i] >= 2) {
                cards[i] -= 2;
                isWin = process(cards);
                cards[i] += 2;
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

    public static boolean process(int[] cards) {
        if(isAllZero(cards)) {
            return true;
        }
        boolean isWin = false;
        for(int i = 0; i < 36; i++) {
            if(cards[i] >= 3) {
                cards[i] -= 3;
                isWin = process(cards);
                cards[i] += 3;
            }
            if(isWin) break;
            if(cards[i] > 0 || cards[i] == -1) {
                if((cards[i+1] > 0 || cards[i+1] == -1) && (cards[i+2] > 0 || cards[i+2] == -1)) {
                    cards[i]--; cards[i+1]--; cards[i+2]--;
                    isWin = process(cards);
                    cards[i]++; cards[i+1]++; cards[i+2]++;
                } else {
                    return false;
                }
            }
            if(isWin) break;
        }
        return isWin;
    }

    public static boolean isAllZero(int[] cards) {
        for (int card : cards) {
            if (card > 0) {
                return false;
            }
        }
        return true;
    }
}
