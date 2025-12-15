package it.polimi.ingsw.Network.Messages.ClientToServer.AdventureCardMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class SlaversLostCardMessage extends Message {
    private final ArrayList<ArrayList<Integer>> removedCrewFrom = new ArrayList<>();

    public SlaversLostCardMessage(MessageType messageType) {
        super(messageType);
    }

    public ArrayList<ArrayList<Integer>> getRemovedCrewFrom() {
        return removedCrewFrom;
    }

    public void setRemovedCrewFrom(int x, int y) {
        ArrayList<Integer> batteryCompartment = new ArrayList<>();
        batteryCompartment.add(x);
        batteryCompartment.add(y);
        removedCrewFrom.add(batteryCompartment);
    }
}
