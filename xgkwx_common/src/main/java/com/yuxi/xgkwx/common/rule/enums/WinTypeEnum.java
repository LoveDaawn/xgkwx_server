package com.yuxi.xgkwx.common.rule.enums;

import lombok.Getter;

@Getter
public enum WinTypeEnum {
    BASE("BASE", "屁胡"),
    KWX("KWX", "卡五星"),
    SEEN_CARD("SEEN_CARD", "亮倒"),
    MSGY("MSGY", "明四归一"),
    DOUBLE_MSGY("DOUBLE_MSGY", "双明四归一"),
    TRIPLE_MSGY("TRIPLE_MSGY", "三明四归一"),
    PPH("PPH", "碰碰胡"),
    ASGY("ASGY", "暗四归一"),
    DOUBLE_ASGY("DOUBLE_ASGY", "双暗四归一"),
    TRIPLE_ASGY("TRIPLE_ASGY", "三暗四归一"),
    QYS("QYS", "清一色"),
    QD("QD", "七对"),
    QG("QG", "抢杠"),
    LQD("LQD", "龙七对"),
    DOUBLE_LQD("DOUBLE_LQD", "双龙七对"),
    TRIPLE_LQD("TRIPLE_LQD", "三龙七对"),
    SZY("SZY", "手抓一"),
    GSK("GSK", "杠上开"),
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
