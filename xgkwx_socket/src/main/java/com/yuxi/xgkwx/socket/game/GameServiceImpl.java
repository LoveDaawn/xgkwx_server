package com.yuxi.xgkwx.socket.game;

import com.alibaba.fastjson.JSONObject;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.msg.res.MessageResponse;
import io.netty.channel.ChannelHandlerContext;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl {

    private final static Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    @Resource
    private GameHandler gameHandler;
}
