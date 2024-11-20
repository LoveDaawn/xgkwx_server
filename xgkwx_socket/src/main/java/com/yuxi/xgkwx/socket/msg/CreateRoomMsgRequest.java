package com.yuxi.xgkwx.socket.msg;

import lombok.Getter;

@Getter
public class CreateRoomMsgRequest extends MessageRequest{

    private String roomId;
}
