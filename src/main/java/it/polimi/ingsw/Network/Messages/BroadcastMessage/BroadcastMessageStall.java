package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.StallMessage;

public class BroadcastMessageStall extends BroadcastMessage {
    private final StallMessage message;
    private final MessageType specificType;

    public BroadcastMessageStall(MessageType messageType, MessageType specificType, StallMessage message) {
        super(messageType, messageType);
        this.message = message;
        this.specificType = specificType;
    }

    public StallMessage getMessage() {
        return message;
    }

    public MessageType getSpecificType() {
        return specificType;
    }
}
