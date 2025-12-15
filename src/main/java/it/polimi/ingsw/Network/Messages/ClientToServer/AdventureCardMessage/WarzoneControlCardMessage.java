package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class WarzoneControlCardMessage extends Message {
    private int parameter = 0;
    private final ArrayList<ArrayList<Integer>> removedBatteryFrom = new ArrayList<>();

    public WarzoneControlCardMessage(MessageType messageType) {
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

    public int getParameter() {
        return parameter;
    }

    public void setParameter(int parameter) {
        this.parameter = parameter;
    }
}
