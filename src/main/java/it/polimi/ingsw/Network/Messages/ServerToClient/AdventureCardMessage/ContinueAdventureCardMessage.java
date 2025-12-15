package it.polimi.ingsw.Network.Messages.ServerToClient.AdventureCardMessage;

import it.polimi.ingsw.Model.AdventureCard.SingleAdventureCard.AdventureCard;
import it.polimi.ingsw.Network.Messages.Message;
import it.polimi.ingsw.Network.Messages.MessageType;

public class ContinueAdventureCardMessage extends Message {
    private final AdventureCard adventureCard;
    private final String currentPlayerUsername;

    public ContinueAdventureCardMessage(MessageType messageType, AdventureCard adventureCard, String currentPlayerUsername) {
        super(messageType);
        this.adventureCard = adventureCard;
        this.currentPlayerUsername = currentPlayerUsername;
    }

    public AdventureCard getAdventureCard() {
        return adventureCard;
    }

    public String getCurrentPlayerUsername() {
        return currentPlayerUsername;
    }
}
