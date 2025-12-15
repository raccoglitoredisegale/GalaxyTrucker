package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Model.ComponentCard.Goods;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SmugglersLostCardMessage extends Message {
    private final Map<ArrayList<Integer>, ArrayList<Goods>> removedGoodsFrom;
    private ArrayList<ArrayList<Integer>> removedBatteryFrom = new ArrayList<>();


    public SmugglersLostCardMessage(MessageType messageType) {
        super(messageType);
        this.removedBatteryFrom = new ArrayList<>();
        this.removedGoodsFrom = new HashMap<>();
    }

    public Map<ArrayList<Integer>, ArrayList<Goods>> getRemovedGoodsFrom() {
        return removedGoodsFrom;
    }

    public void setRemovedGoodsFrom(int x, int y, Goods good) {
        ArrayList<Integer> key = new ArrayList<>();
        key.add(x);
        key.add(y);

        ArrayList<Goods> goodsList = removedGoodsFrom.getOrDefault(key, new ArrayList<>());
        goodsList.add(good);
        removedGoodsFrom.put(key, goodsList);
    }


    public ArrayList<ArrayList<Integer>> getRemovedBatteryFrom() {
        return removedBatteryFrom;
    }

    public void setRemovedBatteryFrom(int x, int y) {
        ArrayList<Integer> batteryCompartment = new ArrayList<>();
        batteryCompartment.add(x);
        batteryCompartment.add(y);
        removedBatteryFrom.add(batteryCompartment);
    }
}
