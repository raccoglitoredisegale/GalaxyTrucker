package it.polimi.ingsw.Network.Messages.BroadcastMessage;

import it.polimi.ingsw.Network.Messages.MessageType;

public class UpdateAdventureCardDeck extends BroadcastMessage {
    private String cardId;

    public UpdateAdventureCardDeck(MessageType messageType, MessageType specificType, String id) {
        super(messageType, specificType);
    }

    public String getCardId() {
        return this.cardId;
    }
}
