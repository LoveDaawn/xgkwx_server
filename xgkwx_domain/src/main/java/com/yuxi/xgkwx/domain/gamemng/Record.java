package com.yuxi.xgkwx.domain.gamemng;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Record {
    private List<Integer> initOrder;
    private Integer initScore;
    private String player1Id;
    private List<Integer> player1InitialOrder;
    private String player2Id;
    private List<Integer> player2InitialOrder;
    private String player3Id;
    private List<Integer> player3InitialOrder;
    private Map<Integer,List<String>> operateOrder;
    private List<String> winTypes;
    private Integer winRate;
    private String winner;
}
