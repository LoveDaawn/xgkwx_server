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
        throw new CommonException(GameExceptionEnums.MESSAGE_TYPE_ERROR);
    }
}
