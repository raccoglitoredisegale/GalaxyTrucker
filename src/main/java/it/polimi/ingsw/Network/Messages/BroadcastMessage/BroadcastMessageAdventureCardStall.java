package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;

import java.util.ArrayList;

public class BroadcastMessageAdventureCardStall extends BroadcastMessage {
    ArrayList<String> stalledPlayers;
    private final MessageType specificType;

    public BroadcastMessageAdventureCardStall(MessageType messageType, MessageType specificType, ArrayList<String> stalledPlayers) {
        super(messageType, messageType);
        this.stalledPlayers = stalledPlayers;
        this.specificType = specificType;
    }

    public ArrayList<String> getStalledPlayers() {
        return stalledPlayers;
    }

    public MessageType getSpecificType() {
        return specificType;
    }

}
