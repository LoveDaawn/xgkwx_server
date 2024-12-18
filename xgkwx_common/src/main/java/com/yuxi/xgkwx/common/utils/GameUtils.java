package com.yuxi.xgkwx.common.utils;

import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.exception.GameExceptionEnums;

public class GameUtils {
    private static volatile boolean[] room = new boolean[1000];

    public static synchronized String genRoomId() {
        for(int i = 0; i < room.length; i++) {
            if(!room[i]) {
                room[i] = true;
                return String.valueOf(i + 10000);
            }
        }
        throw new CommonException(GameExceptionEnums.ROOM_REACH_LIMITED);
    }

    public static String arrayToString(short[] cards) {
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < cards.length; i++) {
            if(cards[i] > 0) {
                appendCard(sb, i, cards[i]);
            } else if(cards[i] == -1) {
                appendCard(sb, i, 1);
            }
        }
        if(sb.isEmpty()) {
            return "";
        }
        return sb.substring(0, sb.length() - 1);
    }

    private static void appendCard(StringBuffer sb, int i, int num) {
        for(int k = 0; k < num; k++) {
            sb.append(i);
            sb.append(",");
        }
    }

}
