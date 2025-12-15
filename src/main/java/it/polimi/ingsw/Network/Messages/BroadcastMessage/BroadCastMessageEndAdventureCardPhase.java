package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.Map;

public class BroadCastMessageEndAdventureCardPhase extends BroadcastMessage {
    Map<String, Integer> arrivalOrder;
    Map<String, Integer> goodsValue;
    Map<String, Integer> exposedConnectors;
    Map<String, Integer> loses;
    Map<String, Integer> totalCredits;
    Map<String, Integer> baseCredits;
    int reason;
    private final String winner;
    private final ArrayList<String> finalRank;
    private final MessageType specificType;

    public BroadCastMessageEndAdventureCardPhase(MessageType messageType, MessageType specificType, String winner, ArrayList<String> finalRank, Map<String, Integer> arrivalOrder
            , Map<String, Integer> goodsValue, Map<String, Integer> exposedConnectors, Map<String, Integer> loses, Map<String, Integer> totalCredits, Map<String, Integer> baseCredits, int reason) {
        super(messageType, messageType);
        this.specificType = specificType;
        this.winner = winner;
        this.finalRank = finalRank;
        this.arrivalOrder = arrivalOrder;
        this.goodsValue = goodsValue;
        this.exposedConnectors = exposedConnectors;
        this.loses = loses;
        this.totalCredits = totalCredits;
        this.baseCredits = baseCredits;
        this.reason = reason;
    }

    public String getWinner() {
        return winner;
    }

    public ArrayList<String> getFinalRank() {
        return finalRank;
    }

    public Map<String, Integer> getArrivalOrder() {
        return arrivalOrder;
    }

    public Map<String, Integer> getGoodsValue() {
        return goodsValue;
    }

    public Map<String, Integer> getExposedConnectors() {
        return exposedConnectors;
    }

    public Map<String, Integer> getLoses() {
        return loses;
    }

    public Map<String, Integer> getTotalCredits() {
        return totalCredits;
    }

    public Map<String, Integer> getBaseCredits() {
        return baseCredits;
    }

    public MessageType getSpecificType() {
        return specificType;
    }

    public int getReason() {
        return reason;
    }

}
