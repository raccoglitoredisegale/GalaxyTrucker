package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class MeteorCardMessage extends Message {
    private boolean canDefend = false;
    private final ArrayList<Integer> removedComponentFrom = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> removedBatteryFrom = new ArrayList<>();

    public MeteorCardMessage(MessageType messageType) {
        super(messageType);
        this.removedBatteryFrom = removedBatteryFrom;

    }

    public ArrayList<ArrayList<Integer>> getRemovedBatteryFrom() {
        return removedBatteryFrom;
    }

    public boolean getCanDefend() {
        return canDefend;
    }

    public void setCanDefend(boolean canDefend) {
        this.canDefend = canDefend;
    }

    public ArrayList<Integer> getRemovedComponentFrom() {
        return removedComponentFrom;
    }

    public void setRemovedComponentFrom(int x, int y) {
        removedComponentFrom.add(x);
        removedComponentFrom.add(y);
    }

    public void setRemovedBatteryFrom(int x, int y) {
        ArrayList<Integer> batteryCompartment = new ArrayList<>();
        batteryCompartment.add(x);
        batteryCompartment.add(y);
        removedBatteryFrom.add(batteryCompartment);
    }
}
