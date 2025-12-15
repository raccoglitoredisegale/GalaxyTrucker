package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.MeteorMessage;

public class BroadcastMessageMeteor extends BroadcastMessage {
    private final MeteorMessage message;
    private final MessageType specificType;

    public BroadcastMessageMeteor(MessageType messageType, MessageType specificType, MeteorMessage message) {
        super(messageType, messageType);
        this.message = message;
        this.specificType = specificType;
    }

    public MeteorMessage getMessage() {
        return message;
    }

    public MessageType getSpecificType() {
        return specificType;
    }
}
