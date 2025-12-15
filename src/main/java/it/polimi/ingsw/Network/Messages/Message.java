package it.polimi.ingsw.Network.Messages;

import java.io.Serializable;

public class Message implements Serializable {
    private final MessageType messageType;

    public Message(MessageType messageType) {
        this.messageType = messageType;
    }

    public MessageType getType() {
        return this.messageType;
    }
}
