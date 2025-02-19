package com.yuxi.xgkwx.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameMsgEnums {

    CREATE_ROOM("CREATE_ROOM", "建立房间"),
    JOIN_ROOM("JOIN_ROOM", "加入房间"),
    PLAYER_JOINED("PLAYER_JOINED", "玩家加入"), //后端发给前端
    LEAVE_ROOM("LEAVE_ROOM", "'离开房间"),
    ROOM_DISSOLVED("ROOM_DISSOLVED", "房间已解散"), //后端发给前端
    PLAYER_LEAVE("PLAYER_LEAVE", "玩家离开"), //后端发给前端
    PREPARE("PREPARE", "准备"),
    PLAYER_PREPARED("PLAYER_PREPARED", "玩家已准备"), //后端发给前端
    CANCEL_PREPARE("CANCEL_PREPARE", "取消准备"),
    PLAYER_CANCELED_PREPARATION("PLAYER_CANCELED_PREPARATION", "玩家取消准备"), //后端发给前端
    GAME_START("GAME_START", "开始游戏"),
    GAME_INIT("GAME_INIT", "初始化游戏"), //后端发给前端
    IN("IN", "进牌"),//后端发给前端
    PLAYER_IN("PLAYER_IN", "玩家进牌"), //后端发给前端
    OUT("OUT", "出牌"),
    OUT_PROP("OUT_PROP", "广播出牌"), //后端发给前端
    ACK("ACK", "确认"),
    WAIT_OP("WAIT_OP", "等待决策"), //后端发给前端
    WAIT("WAIT", "等待读秒"), //后端发给前端
    SKIP("SKIP", "过"),
    END_WAIT("END_WAIT", "等待结束"),//后端发给前端
    PENG("PENG", "碰"),
    PENG_PROP("PENG_PROP", "确认碰牌"), //后端发给前端
    GANG("GANG", "碰"),
    GANG_PROP("GANG_PROP", "确认杠牌"), //后端发给前端
    WIN("WIN", "胡"),
    CONFIRM_WIN("CONFIRM_WIN", "确认胡牌"), //后端发给前端
    ;


    private final String code;
    private final String info;

    public static GameMsgEnums getGameMsgByCode(String code) {
        for(GameMsgEnums e : GameMsgEnums.values()) {
            if(e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }


}
