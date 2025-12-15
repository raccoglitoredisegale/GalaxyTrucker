package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.Map;

public class AbandonedStationCardMessage extends Message {
    private final Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap;

    public AbandonedStationCardMessage(MessageType messageType, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) {
        super(messageType);

        this.goodsMap = goodsMap;
    }

    public Map<ArrayList<Integer>, ArrayList<Goods>> getGoodsMap() {
        return goodsMap;
    }
}
