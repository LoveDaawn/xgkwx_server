package com.yuxi.xgkwx.socket.game;

import com.yuxi.xgkwx.common.enums.GameMsgEnums;
import com.yuxi.xgkwx.common.rule.enums.OperationTypeEnum;
import com.yuxi.xgkwx.common.utils.RuleUtil;
import com.yuxi.xgkwx.domain.gaming.player.PlayerCardsVo;
import com.yuxi.xgkwx.domain.gaming.player.PlayerChannelVo;
import com.yuxi.xgkwx.domain.gaming.room.GameInfo;
import com.yuxi.xgkwx.domain.gaming.room.OperationCardVo;
import com.yuxi.xgkwx.domain.gaming.room.RoomVo;
import com.yuxi.xgkwx.socket.msg.MessageService;
import com.yuxi.xgkwx.socket.msg.req.MessageRequest;
import com.yuxi.xgkwx.socket.msg.req.content.GangContent;
import com.yuxi.xgkwx.socket.msg.req.content.PengContent;
import com.yuxi.xgkwx.socket.msg.req.content.WinContent;
import com.yuxi.xgkwx.socket.msg.res.content.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GameHandler {
    @Resource
    private MessageService messageService;

    /**
     * 碰杠胡检测
     *
     * @param messageRequest         消息体
     * @param roomVo                 房间值对象
     * @param targetPlayerCardsVoMap 玩家手牌映射表
     * @param card                   待决策牌
     * @return 检测结果
     */
    public Map<PlayerChannelVo, OperationCardVo> pengGangWinCheck(MessageRequest messageRequest, RoomVo roomVo, Map<String, PlayerCardsVo> targetPlayerCardsVoMap, String card) {
        Map<PlayerChannelVo, OperationCardVo> opMap = new HashMap<>();
        roomVo.getPlayers().forEach(player -> {
            //除了发牌者
            if (!player.getUnifyId().equals(messageRequest.getUnifyId())) {
                Map<String, String> listenMap = targetPlayerCardsVoMap.get(player.getUnifyId()).getListenMap();
                short[] cards = targetPlayerCardsVoMap.get(player.getUnifyId()).getPlayerHandCards();
                OperationCardVo operationCardVo = null;
                //胡牌检测
                if (listenMap.containsKey(card) && Integer.parseInt(listenMap.get(card)) > 1) {
                    operationCardVo = new OperationCardVo(player.getUnifyId(), Short.parseShort(card), OperationTypeEnum.WIN.getCode());
                    opMap.put(player, operationCardVo);
                }
                //杠牌检测
                if (RuleUtil.gangCheck(cards, Short.parseShort(card), false)) {
                    operationCardVo = operationCardVo == null ? new OperationCardVo(player.getUnifyId(), Short.parseShort(card), OperationTypeEnum.GANG.getCode()) : operationCardVo;
                    opMap.put(player, operationCardVo);
                }
                //碰牌检测
                if (RuleUtil.pengCheck(cards, Short.parseShort(card))) {
                    opMap.put(player, new OperationCardVo(player.getUnifyId(), Short.parseShort(card), OperationTypeEnum.PENG.getCode()));
                }
            }
        });
        return opMap;
    }

    public WaitOpContent buildWaitOpContent(OperationCardVo action) {
        WaitOpContent waitOpContent = new WaitOpContent();
        waitOpContent.setCard(String.valueOf(action.getCard()));
        waitOpContent.setPENG(action.canPeng());
        waitOpContent.setGANG(action.canGang());
        waitOpContent.setWIN(action.canWin());
        waitOpContent.setTimeWait("30");
        return waitOpContent;
    }

    public CardInContent buildCardInContent(String card, short[] cards, boolean cardinFlag) {
        boolean gang = RuleUtil.gangCheck(cards, Short.parseShort(card), cardinFlag);
        boolean win = RuleUtil.winCheck(cards, Short.parseShort(card));
        return new CardInContent(card, gang, win, "30");
    }

    public Map<String, List<String>> opMapToOrderMap(Map<PlayerChannelVo, OperationCardVo> opMap) {
        if (opMap == null || opMap.isEmpty()) {
            return null;
        }
        Map<String, List<String>> orderMap = new HashMap<>();
        //胡牌者
        opMap.forEach((k, v) -> {
            if (v.canWin()) {
                if (orderMap.get(OperationTypeEnum.WIN.getCode()) == null) {
                    ArrayList<String> winList = new ArrayList<>();
                    winList.add(k.getUnifyId());
                    orderMap.put(OperationTypeEnum.WIN.getCode(), orderMap.getOrDefault(OperationTypeEnum.WIN.getCode(), winList));
                } else {
                    orderMap.get(OperationTypeEnum.WIN.getCode()).add(k.getUnifyId());
                }
            }
            if (v.canGang() || v.canPeng()) {
                ArrayList<String> pengGangList = new ArrayList<>();
                pengGangList.add(k.getUnifyId());
                orderMap.put(OperationTypeEnum.PENG.getCode(), pengGangList);
            }
        });
        //碰杠
        return orderMap;
    }

    public void pengCard(String unifyId, PengContent content, RoomVo roomVo) {
        //碰牌
        GameInfo gameInfo = roomVo.getGameInfo();
        gameInfo.peng(unifyId, content.getCard());

        //发送消息
        messageService.sendCustomMessageToAllPlayers(roomVo, GameMsgEnums.PENG_PROP, new PengPropContent(unifyId, content.getCard(), content.getCardOutUnifyId()));
    }

    public void gangCard(String unifyId, GangContent content, RoomVo roomVo) {
        //杠牌
        GameInfo gameInfo = roomVo.getGameInfo();
        gameInfo.gang(unifyId, content.getCard(), content.getGangType());
        //发送消息
        messageService.sendCustomMessageToAllPlayers(roomVo, GameMsgEnums.GANG_PROP, new GangPropContent(unifyId, content.getCard(), content.getCardOutUnifyId(), content.getGangType()));
    }

    public ConfirmWinContent win(String unifyId, WinContent winContent, RoomVo roomVo, GameInfo gameInfo) {
        PlayerCardsVo playerCardsVo = gameInfo.getPlayerCardsMap().get(unifyId);
        List<String> winType = RuleUtil.queryWinType(playerCardsVo.getPlayerHandCards(), Short.parseShort(winContent.getCard()));

        ConfirmWinContent confirmWinContent = new ConfirmWinContent();
        confirmWinContent.setWinType(winType);
        confirmWinContent.setUnifyid(unifyId);
        confirmWinContent.setCard(winContent.getCard());
        confirmWinContent.setMultiple(16);
        return confirmWinContent;
    }


    public void removeWhenExist(Map<String, List<String>> waitOrderMap, String unifyId) {
        List<String> winList = waitOrderMap.get(OperationTypeEnum.WIN.getCode());
        if (winList != null && !winList.isEmpty()) {
            winList.remove(unifyId);
        }
        List<String> pengList = waitOrderMap.get(OperationTypeEnum.PENG.getCode());
        if (pengList != null && !pengList.isEmpty()) {
            pengList.remove(unifyId);
        }
        List<String> gangList = waitOrderMap.get(OperationTypeEnum.GANG.getCode());
        if (gangList != null && !gangList.isEmpty()) {
            gangList.remove(unifyId);
        }

    }
}
