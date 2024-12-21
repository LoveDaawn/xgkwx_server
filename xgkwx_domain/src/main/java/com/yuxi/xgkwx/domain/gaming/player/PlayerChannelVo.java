package com.yuxi.xgkwx.domain.gaming.player;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PlayerChannelVo {
    private String unifyId;
    private Channel channel;
}
