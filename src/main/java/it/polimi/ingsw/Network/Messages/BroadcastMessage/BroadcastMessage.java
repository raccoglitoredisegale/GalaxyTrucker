package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class BroadcastMessage extends Message {
    private final MessageType specificType;
    private Message message;

    public BroadcastMessage(MessageType messageType, MessageType specificType) {
        super(messageType);
        this.specificType = specificType;
    }

    public MessageType getSpecificType() {
        return specificType;
    }
}
