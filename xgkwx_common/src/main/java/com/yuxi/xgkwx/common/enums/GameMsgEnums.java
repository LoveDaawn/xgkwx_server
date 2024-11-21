package com.yuxi.xgkwx.common.enums;

import lombok.Getter;

@Getter
public enum GameMsgEnums {

    CREATE_ROOM("CREATE_ROOM", "建立房间");


    private String code;
    private String info;

    GameMsgEnums (String code, String info) {
        this.code = code;
        this.info = info;
    }

    public static GameMsgEnums getGameMsgByCode(String code) {
        for(GameMsgEnums e : GameMsgEnums.values()) {
            if(e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }


}
