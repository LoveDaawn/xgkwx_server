package com.yuxi.xgkwx.socket;

import com.yuxi.xgkwx.socket.websocket.WebSocketInitialization;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WebsocketApplication {
    private final static Logger log = LoggerFactory.getLogger(WebsocketApplication.class);
    @Resource
    private WebSocketInitialization websocketInitialization;

    @PostConstruct
    public void start() {
        try {
            log.info(Thread.currentThread().getName() + ":websocket启动中......");
            websocketInitialization.init();
            log.info(Thread.currentThread().getName() + ":websocket启动成功！！！");
        } catch (Exception e) {
            log.error("websocket发生错误：",e);
        }
    }
}
