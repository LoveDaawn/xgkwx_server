package com.yuxi.xgkwx.common.rule.enums;

import lombok.Getter;

@Getter
public enum WinTypeEnum {
    BASE("BASE", "屁胡"),
    KWX("KWX", "卡五星"),
    SEEN_CARD("SEEN_CARD", "亮倒"),
    MSGY("MSGY", "明四归一"),
    PPH("PPH", "碰碰胡"),
    ASGY("ASGY", "暗四归一"),
    QYS("QYS", "清一色"),
    QD("QD", "七对"),
    QG("QG", "抢杠"),
    LQD("LQD", "龙七对"),
    SZ("SZ", "数子"),
    SK("SK", "数坎")
    ;
    private String code;
    private String desc;

    WinTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
