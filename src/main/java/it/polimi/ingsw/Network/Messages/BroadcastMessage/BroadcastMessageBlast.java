package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.BlastMessage;

public class BroadcastMessageBlast extends BroadcastMessage {
    private final BlastMessage message;
    private final MessageType specificType;

    public BroadcastMessageBlast(MessageType messageType, MessageType specificType, BlastMessage message) {
        super(messageType, messageType);
        this.message = message;
        this.specificType = specificType;
    }

    public BlastMessage getMessage() {
        return message;
    }

    public MessageType getSpecificType() {
        return specificType;
    }
}
