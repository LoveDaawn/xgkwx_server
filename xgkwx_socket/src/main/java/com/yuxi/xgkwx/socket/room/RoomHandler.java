package com.yuxi.xgkwx.socket.room;

import com.alibaba.fastjson2.JSON;
import com.yuxi.xgkwx.common.constants.GamingConstant;
import com.yuxi.xgkwx.common.enums.GameMsgEnums;
import com.yuxi.xgkwx.common.exception.CommonException;
import com.yuxi.xgkwx.common.exception.GameExceptionEnums;
import com.yuxi.xgkwx.common.rule.enums.OperationTypeEnum;
import com.yuxi.xgkwx.common.utils.CollectionUtil;
import com.yuxi.xgkwx.common.utils.GameUtils;
import com.yuxi.xgkwx.common.utils.RuleUtil;
import com.yuxi.xgkwx.domain.gaming.player.PlayerCardsVo;
import com.yuxi.xgkwx.domain.gaming.player.PlayerChannelVo;
import com.yuxi.xgkwx.domain.gaming.room.GameInfo;
import com.yuxi.xgkwx.domain.gaming.room.OperationCardVo;
import com.yuxi.xgkwx.domain.gaming.room.OperationVo;
import com.yuxi.xgkwx.domain.gaming.room.RoomVo;
import com.yuxi.xgkwx.socket.game.GameHandler;
import com.yuxi.xgkwx.socket.msg.MessageService;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.msg.req.content.*;
import com.yuxi.xgkwx.socket.msg.res.MessageResponse;
import com.yuxi.xgkwx.socket.msg.res.MessageResponseUtils;
import com.yuxi.xgkwx.socket.msg.res.content.*;
import com.yuxi.xgkwx.socket.msg.res.room.CreateRoomMsgRes;
import com.yuxi.xgkwx.socket.msg.res.room.JoinRoomMsgRes;
import io.netty.channel.Channel;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class RoomHandler {
    // 用户管理所有房间，由房间号进行索引
    private static volatile Map<String, RoomVo> roomManagerChannelMap;

    //用户反向索引房间号
    private static volatile Map<String, String> playersToRoomMap;

    @Resource
    private MessageService messageService;

    @Resource
    private GameHandler gameHandler;

    @PostConstruct
    public void init() {
        roomManagerChannelMap = new ConcurrentHashMap<>();
        playersToRoomMap = new ConcurrentHashMap<>();
    }

    /**
     * 创建房间
     *
     * @param channel 通信channel
     * @return 房间号id
     */
    public MessageResponse<CreateRoomMsgRes> createRoom(Channel channel, MessageRequest messageRequest) {
        CreateRoomContent content = JSON.parseObject(messageRequest.getContent(), CreateRoomContent.class);
        RoomVo roomVo = new RoomVo();
        //创建房间对玩家映射的列表
        List<PlayerChannelVo> players = new CopyOnWriteArrayList<>();
        players.add(new PlayerChannelVo(messageRequest.getUnifyId(), channel, false));
        String roomId = GameUtils.genRoomId();
        roomVo.setRoomId(roomId)
                .setPlayers(players)
                .setRules(content.getRules())
                .setRoomMaster(messageRequest.getUnifyId());
        roomManagerChannelMap.put(roomId, roomVo);
        playersToRoomMap.put(messageRequest.getUnifyId(), roomId);
        CreateRoomMsgRes crm = new CreateRoomMsgRes(roomId);
        return MessageResponseUtils.responseSuccess(crm, messageRequest.getMessageId());
    }

    /**
     * 加入房间
     *
     * @param channel 通信channel
     * @return 房间号id
     */
    public MessageResponse<JoinRoomMsgRes> joinRoom(Channel channel, MessageRequest messageRequest) {
        JoinRoomContent content = JSON.parseObject(messageRequest.getContent(), JoinRoomContent.class);
        String playerUnifyId = messageRequest.getUnifyId();
        RoomVo roomVo = roomManagerChannelMap.get(content.getRoomId());
        if (roomVo == null) {
            messageService.sendDefaultErrorMessage(channel, GameExceptionEnums.FIND_ROOM_ERROR, playerUnifyId);
            throw new CommonException(GameExceptionEnums.FIND_ROOM_ERROR);
        }
        if (roomVo.getPlayers().size() == 3) {
            messageService.sendDefaultErrorMessage(channel, GameExceptionEnums.ROOM_FULL, playerUnifyId);
            throw new CommonException(GameExceptionEnums.ROOM_FULL);
        }
        //将新玩家加入到房间中
        roomVo.getPlayers().add(new PlayerChannelVo(playerUnifyId, channel, false));
        String position = roomVo.getPositionByIndex(playerUnifyId);
        playersToRoomMap.put(playerUnifyId, content.getRoomId());
        JoinRoomMsgRes r = new JoinRoomMsgRes(position, roomVo.getRules(), roomVo.buildPlayersInfo());
        //通知房间内所有玩家有新玩家加入
        messageService.sendCustomMessageExceptSelf(roomVo, GameMsgEnums.PLAYER_JOINED, playerUnifyId, new PlayerJoinedContent(playerUnifyId, position));
        return MessageResponseUtils.responseSuccess(r, messageRequest.getMessageId());
    }

    /**
     * 离开房间。如果房主离开则解散房间，向所有玩家广播消息；否则向房间内其他玩家广播玩家离开消息
     *
     * @param messageRequest 通信channel
     * @return true：房主离开  false：玩家离开
     */
    public MessageResponse<Void> leaveRoom(MessageRequest messageRequest) {
        String roomId = playersToRoomMap.get(messageRequest.getUnifyId());
        RoomVo roomVo = roomManagerChannelMap.get(roomId);
        if (roomVo == null) {
            throw new CommonException(GameExceptionEnums.FIND_ROOM_ERROR);
        }
        //房主离开：解散房间
        if (roomVo.getRoomMaster().equals(messageRequest.getUnifyId())) {
            //遍历房间内的玩家列表
            roomVo.getPlayers().forEach(player -> {
                //玩家映射房间号表中移除玩家
                playersToRoomMap.remove(player.getUnifyId());
                //除了房主
                if (!roomVo.getRoomMaster().equals(player.getUnifyId())) {
                    //向所有玩家广播房间已解消息
                    messageService.sendNoteMessage(player.getChannel(), GameMsgEnums.ROOM_DISSOLVED, player.getUnifyId());
                }
            });
            //管理房间map移除此房间
            roomManagerChannelMap.remove(roomId);
        } else {
            //玩家映射房间号表中移除玩家
            playersToRoomMap.remove(messageRequest.getUnifyId());
            //玩家离开
            roomVo.getPlayers().forEach(player -> {
                //除了该玩家
                if (!player.getUnifyId().equals(messageRequest.getUnifyId())) {
                    //向所有玩家广播房间玩家离开
                    messageService.sendSignalMessage(player.getChannel(), GameMsgEnums.ROOM_DISSOLVED, player.getUnifyId(), messageRequest.getUnifyId());
                } else {
                    //移除该玩家
                    roomVo.getPlayers().remove(player);
                }
            });
        }
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }

    /**
     * 玩家准备
     *
     * @param messageRequest 通信channel
     */
    public MessageResponse<Void> prepare(MessageRequest messageRequest) {
        RoomVo roomVo = getRoomVo(messageRequest);
        PlayerChannelVo playerChannelVo = roomVo.selectPlayer(messageRequest.getUnifyId());
        if (playerChannelVo == null) {
            return MessageResponseUtils.responseFail(GameExceptionEnums.FIND_PLAYER_ERROR, messageRequest.getMessageId());
        }
        playerChannelVo.setPrepared(true);
        //向房间内所有玩家广播玩家准备消息
        messageService.sendSignalMessageToAllPlayers(roomVo, GameMsgEnums.PLAYER_PREPARED, messageRequest.getUnifyId());
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }

    /**
     * 玩家取消准备
     *
     * @param messageRequest 通信channel
     */
    public MessageResponse<Void> cancelPrepare(MessageRequest messageRequest) {
        RoomVo roomVo = getRoomVo(messageRequest);
        PlayerChannelVo playerChannelVo = roomVo.selectPlayer(messageRequest.getUnifyId());
        if (playerChannelVo == null) {
            return MessageResponseUtils.responseFail(GameExceptionEnums.FIND_PLAYER_ERROR, messageRequest.getMessageId());
        }
        playerChannelVo.setPrepared(false);
        //向房间内所有玩家广播玩家取消准备消息
        messageService.sendSignalMessageToAllPlayers(roomVo, GameMsgEnums.PLAYER_CANCELED_PREPARATION, messageRequest.getUnifyId());
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }

    public MessageResponse<Void> startGame(MessageRequest messageRequest) {
        RoomVo roomVo = getRoomVo(messageRequest);
        roomVo.setBanker(roomVo.getRoomMaster());
        if (roomVo.getPlayers().size() != 3 || !roomVo.getPlayers().stream().allMatch(PlayerChannelVo::isPrepared)) {
            return MessageResponseUtils.responseFail(GameExceptionEnums.PLAYER_UNPREPARED, messageRequest.getMessageId());
        }
        //初始化游戏信息，给所有玩家发牌
        GameInfo gameInfo = new GameInfo();
        gameInfo.init(roomVo.getPlayers());
        roomVo.setGameInfo(gameInfo);
        roomVo.getPlayers().forEach(player -> {
            short[] playerHandCards = gameInfo.getPlayerCardsMap().get(player.getUnifyId()).getPlayerHandCards();
            messageService.sendCustomMessage(player.getChannel(), GameMsgEnums.GAME_INIT, player.getUnifyId(), new DefaultContent(GameUtils.arrayToString(playerHandCards)));
        });

        //庄家再进一张牌
        String roomMaster = roomVo.getRoomMaster();
        String card = gameInfo.cardIn(roomMaster);
        //进牌消息
        short[] playerHandCards = gameInfo.getPlayerCardsMap().get(roomMaster).getPlayerHandCards();
        messageService.sendCustomMessage(roomVo.selectPlayer(roomMaster).getChannel(), GameMsgEnums.IN, roomMaster, gameHandler.buildCardInContent(card, playerHandCards, true)); //TODO: 配置读取
        //其他玩家收到【玩家进牌】消息
        roomVo.getPlayers().forEach(player -> {
            //除了庄家
            if (!roomMaster.equals(player.getUnifyId())) {
                //向其他玩家广播【玩家进牌】消息
                messageService.sendCustomMessage(player.getChannel(), GameMsgEnums.PLAYER_IN, player.getUnifyId(), new OtherPlayerInContent(roomMaster, "30"));
            }
        });
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }

    public MessageResponse<Void> cardOut(MessageRequest messageRequest, OutContent content) {
        RoomVo roomVo = getRoomVo(messageRequest);
        GameInfo gameInfo = roomVo.getGameInfo();
        String card = content.getCard();
        Map<String, PlayerCardsVo> targetPlayerCardsVoMap = gameInfo.getPlayerCardsMap();
        short[] targetPlayerCards = targetPlayerCardsVoMap.get(messageRequest.getUnifyId()).getPlayerHandCards();
        //扣减对应牌
        gameInfo.cardOut(messageRequest.getUnifyId(), content.getCard());

        //首先检测碰、杠、胡牌，并加入到通知队列中
        Map<PlayerChannelVo, OperationCardVo> opMap = gameHandler.pengGangWinCheck(messageRequest, roomVo, targetPlayerCardsVoMap, card);

        //广播出牌消息
        roomVo.getPlayers().forEach(player -> {
            //除了发牌者
            if (!player.getUnifyId().equals(messageRequest.getUnifyId())) {
                messageService.sendCustomMessage(player.getChannel(), GameMsgEnums.OUT_PROP, player.getUnifyId(),
                        new CardOutContent(card, messageRequest.getUnifyId(), content.getRound(), null));
            }
        });

        //出牌者听牌判断
        List<Short> listenedCards = RuleUtil.listenCheck(targetPlayerCards);
        if(listenedCards != null && !listenedCards.isEmpty()) {
            listenedCards.forEach(listenedCard -> {
                if(RuleUtil.feedCheck(targetPlayerCards, listenedCard, targetPlayerCardsVoMap.get(messageRequest.getUnifyId()).isLdFlag(), false)) {
                    targetPlayerCardsVoMap.get(messageRequest.getUnifyId()).getListenMap().put(String.valueOf(listenedCard), GamingConstant.DA_HU);
                }
                targetPlayerCardsVoMap.get(messageRequest.getUnifyId()).getListenMap().put(String.valueOf(listenedCard), GamingConstant.PI_HU);
            });
        }

        //如果没有碰杠胡
        if(opMap.isEmpty()) {  //如果没有碰杠胡，则将对应出牌加入到玩家出牌list中，同时进行发牌
            //加到对应list中
            roomVo.getGameInfo().getPlayerCardsMap().get(messageRequest.getUnifyId()).getPlayerOutsCardList().add(card);
            //获取下一个玩家
            PlayerChannelVo playerVo = roomVo.getPlayers().get((roomVo.selectPlayerIndex(messageRequest.getUnifyId()) + 1) % 3);
            //发牌
            String nextCard = gameInfo.cardIn(playerVo.getUnifyId());
            //进牌消息
            short[] playerHandCards = gameInfo.getPlayerCardsMap().get(messageRequest.getUnifyId()).getPlayerHandCards();
            messageService.sendCustomMessage(playerVo.getChannel(), GameMsgEnums.IN, playerVo.getUnifyId(), gameHandler.buildCardInContent(nextCard, playerHandCards, true));
            //其他玩家收到【玩家进牌】消息
            roomVo.getPlayers().forEach(player -> {
                //除了庄家
                if (!playerVo.getUnifyId().equals(player.getUnifyId())) {
                    //向其他玩家广播【玩家进牌】消息
                    messageService.sendCustomMessage(player.getChannel(), GameMsgEnums.PLAYER_IN, player.getUnifyId(), new OtherPlayerInContent(playerVo.getUnifyId(), "30"));
                }
            });
        } else {
            //有碰杠胡，那么按照碰杠胡的消息发送给对应玩家
            roomVo.getPlayers().forEach(player -> {
                if(opMap.containsKey(player)) {
                    messageService.sendCustomMessage(player.getChannel(), GameMsgEnums.WAIT_OP, player.getUnifyId(),
                            gameHandler.buildWaitOpContent(opMap.get(player)));
                } else {
                    //对剩余玩家发送等待决策消息
                    messageService.sendCustomMessage(player.getChannel(), GameMsgEnums.WAIT, player.getUnifyId(),
                            new WaitContent("30"));
                }
            });
            roomVo.setWaitOrderMap(gameHandler.opMapToOrderMap(opMap));
        }
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }

    public MessageResponse<Void> skip(MessageRequest messageRequest) {
        RoomVo roomVo = getRoomVo(messageRequest);
        Map<String, List<String>> waitOrderMap = roomVo.getWaitOrderMap();
        gameHandler.removeWhenExist(waitOrderMap, messageRequest.getUnifyId());

        if(CollectionUtil.isEmpty(waitOrderMap.get(OperationTypeEnum.WIN.getCode())) && CollectionUtil.isEmpty(waitOrderMap.get(OperationTypeEnum.PENG.getCode()))) {
            messageService.sendNoteMessageToAllPlayers(roomVo, GameMsgEnums.END_WAIT);
        } else {
            if(CollectionUtil.isEmpty(waitOrderMap.get(OperationTypeEnum.WIN.getCode()))) {
                Map<String, OperationVo> operationMap = roomVo.getOperationMap();
                if(operationMap.containsKey(OperationTypeEnum.PENG.getCode())) {
                    OperationVo operationVo = operationMap.get(OperationTypeEnum.PENG.getCode());
                    gameHandler.pengCard(operationVo.getUnifyId(), new PengContent(operationVo.getCard()), roomVo);
                }
                if(operationMap.containsKey(OperationTypeEnum.GANG.getCode())) {
                    OperationVo operationVo = operationMap.get(OperationTypeEnum.GANG.getCode());
                    gameHandler.gangCard(messageRequest.getUnifyId(), new GangContent(operationVo.getCard(),operationVo.getGangType() ), roomVo);
                }
            }
        }
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }

    public MessageResponse<Void> peng(MessageRequest messageRequest, PengContent content) {
        RoomVo roomVo = getRoomVo(messageRequest);
        Map<String, List<String>> waitOrderMap = roomVo.getWaitOrderMap();
        //还有赢牌的等待决策
        if(!CollectionUtil.isEmpty(waitOrderMap.get(OperationTypeEnum.WIN.getCode()))) {
            Map<String, OperationVo> operationMap = new HashMap<>();
            operationMap.put(OperationTypeEnum.PENG.getCode(), new OperationVo(messageRequest.getUnifyId(), OperationTypeEnum.PENG.getCode(), content.getCard(), null));
            roomVo.setOperationMap(operationMap);
            return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
        }
        gameHandler.pengCard(messageRequest.getUnifyId(), content, roomVo);

        roomVo.setOperationMap(null);
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }



    public MessageResponse<Void> gang(MessageRequest messageRequest, GangContent content) {
        RoomVo roomVo = getRoomVo(messageRequest);
        Map<String, List<String>> waitOrderMap = roomVo.getWaitOrderMap();
        //还有赢牌的等待决策
        if(!CollectionUtil.isEmpty(waitOrderMap.get(OperationTypeEnum.WIN.getCode()))) {
            Map<String, OperationVo> operationMap = new HashMap<>();
            operationMap.put(OperationTypeEnum.GANG.getCode(), new OperationVo(messageRequest.getUnifyId(), OperationTypeEnum.GANG.getCode(), content.getCard(), content.getGangType()));
            roomVo.setOperationMap(operationMap);
            return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
        }
        gameHandler.gangCard(messageRequest.getUnifyId(), content, roomVo);

        roomVo.setOperationMap(null);
        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }



    public MessageResponse<Void> win(MessageRequest messageRequest, WinContent content) {
        RoomVo roomVo = getRoomVo(messageRequest);
        //胡牌
        ConfirmWinContent win = gameHandler.win(messageRequest.getUnifyId(), content, roomVo, roomVo.getGameInfo());
        //发送消息
        messageService.sendCustomMessageToAllPlayers(roomVo, GameMsgEnums.CONFIRM_WIN, win);
        clearEnv(roomVo);

        return MessageResponseUtils.responseSuccess(messageRequest.getMessageId());
    }


    private void clearEnv(RoomVo roomVo) {
        roomVo.setOperationMap(null);
        roomVo.setWaitOrderMap(null);
        roomVo.setGameInfo(null);
    }

    private RoomVo getRoomVo(MessageRequest messageRequest) {
        String roomId = playersToRoomMap.get(messageRequest.getUnifyId());
        return roomManagerChannelMap.get(roomId);
    }

}
