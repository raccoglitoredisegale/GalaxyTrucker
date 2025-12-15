package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;
import it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage.ContinueAdventureCardMessage;

public class BroadcastMessageAdventureCard extends BroadcastMessage {
    private final ContinueAdventureCardMessage message;
    private final MessageType specificType;

    public BroadcastMessageAdventureCard(MessageType messageType, MessageType specificType, ContinueAdventureCardMessage message) {
        super(messageType, messageType);
        this.message = message;
        this.specificType = specificType;
    }

    public ContinueAdventureCardMessage getMessage() {
        return message;
    }

    public MessageType getSpecificType() {
        return specificType;
    }
}
