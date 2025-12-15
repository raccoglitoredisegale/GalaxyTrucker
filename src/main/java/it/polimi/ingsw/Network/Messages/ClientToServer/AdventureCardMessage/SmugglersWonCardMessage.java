package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class SmugglersWonCardMessage extends Message {
    private int cannonPower = 0;
    private final ArrayList<ArrayList<Integer>> removedBatteryFrom = new ArrayList<>();

    public SmugglersWonCardMessage(MessageType messageType) {
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

    public int getCannonPower() {
        return cannonPower;
    }

    public void setCannonPower(int cannonPower) {
        this.cannonPower = cannonPower;
    }
}
