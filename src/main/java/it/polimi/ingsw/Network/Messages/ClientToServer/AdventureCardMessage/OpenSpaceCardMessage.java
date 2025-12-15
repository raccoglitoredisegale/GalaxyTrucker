package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class OpenSpaceCardMessage extends Message {
    private int enginePower = 0;
    private final ArrayList<ArrayList<Integer>> removedBatteryFrom = new ArrayList<>();

    public OpenSpaceCardMessage(MessageType messageType) {
        super(messageType);
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

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }
}
