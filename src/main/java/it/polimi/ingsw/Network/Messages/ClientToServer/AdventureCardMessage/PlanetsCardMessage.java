package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.Map;

public class PlanetsCardMessage extends Message {
    private final int occupiedPlanetIndex;
    private final Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap;

    public PlanetsCardMessage(MessageType messageType, int occupiedPlanetIndex, Map<ArrayList<Integer>, ArrayList<Goods>> goodsMap) {
        super(messageType);
        this.occupiedPlanetIndex = occupiedPlanetIndex;
        this.goodsMap = goodsMap;
    }

    public int getOccupiedPlanetIndex() {
        return occupiedPlanetIndex;
    }

    public Map<ArrayList<Integer>, ArrayList<Goods>> getGoodsMap() {
        return goodsMap;
    }
}
