package com.yuxi.xgkwx.socket.msg;

import com.alibaba.fastjson.JSONObject;
import com.yuxi.xgkwx.common.enums.GameMsgEnums;
import com.yuxi.xgkwx.common.exception.GameExceptionEnums;
import com.yuxi.xgkwx.domain.gaming.room.RoomVo;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.msg.req.content.DefaultContent;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public void sendSignalMessageToAllPlayers(RoomVo roomVo, GameMsgEnums gme) {
        //遍历房间内的玩家列表
        roomVo.getPlayers().forEach(player -> {
            //通知玩家有有玩家准备
            sendSignalMessage(player.getChannel(), gme, player.getUnifyId());
        });
    }

    /**
     * 发送通知类消息，content的info内容为gee.info
     *
     * @param channel 连接channel
     * @param gme     消息类型枚举
     * @param unifyId 用户id
     */
    public void sendNoteMessage(Channel channel, GameMsgEnums gme, String unifyId) {
        MessageRequest mq = new MessageRequest()
                .setMessageType(gme.getCode())
                .setContent(JSONObject.toJSONString(new DefaultContent(gme.getInfo())))
                .setUnifyId(unifyId);
        channel.writeAndFlush(JSONObject.toJSONString(mq));
    }

    /**
     * 发送标记类消息，content的info内容为unifyId
     *
     * @param channel 连接channel
     * @param gme     消息类型枚举
     * @param unifyId 用户id
     */
    public void sendSignalMessage(Channel channel, GameMsgEnums gme, String unifyId) {
        MessageRequest mq = new MessageRequest()
                .setMessageType(gme.getCode())
                .setContent(JSONObject.toJSONString(new DefaultContent(unifyId)))
                .setUnifyId(unifyId);
        channel.writeAndFlush(JSONObject.toJSONString(mq));
    }

    /**
     * 发送自定义Default消息，content的info内容为customMessage
     *
     * @param channel 连接channel
     * @param gme     消息类型枚举
     * @param unifyId 用户id
     * @param customMessage 自定义消息体
     */
    public void sendCustomMessage(Channel channel, GameMsgEnums gme, String unifyId, Object customMessage) {
        MessageRequest mq = new MessageRequest()
                .setMessageType(gme.getCode())
                .setContent(JSONObject.toJSONString(customMessage))
                .setUnifyId(unifyId);
        channel.writeAndFlush(JSONObject.toJSONString(mq));
    }

    /**
     * 发送错误类消息
     *
     * @param channel 连接channel
     * @param gee     错误枚举
     * @param unifyId 用户id
     */
    public void sendDefaultErrorMessage(Channel channel, GameExceptionEnums gee, String unifyId) {
        MessageRequest mq = new MessageRequest()
                .setMessageType(gee.getCode())
                .setContent(JSONObject.toJSONString(new DefaultContent(gee.getMsg())))
                .setUnifyId(unifyId);
        channel.writeAndFlush(JSONObject.toJSONString(mq));
    }
}
