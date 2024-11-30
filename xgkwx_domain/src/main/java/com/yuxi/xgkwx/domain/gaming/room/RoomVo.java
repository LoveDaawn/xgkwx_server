package com.yuxi.xgkwx.domain.gaming.room;

import com.yuxi.xgkwx.domain.gaming.rule.RuleVO;
import io.netty.channel.Channel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class RoomVo {
    private String roomId;
    private List<String> players;
    private String roomType;
    private Map<String, String> rules;
    private List<Channel> roomConn;
}
